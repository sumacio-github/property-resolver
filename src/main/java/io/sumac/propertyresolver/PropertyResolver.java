package io.sumac.propertyresolver;

import static io.sumac.propertyresolver.TypeTransformer.isBoolean;
import static io.sumac.propertyresolver.TypeTransformer.isDouble;
import static io.sumac.propertyresolver.TypeTransformer.isFloat;
import static io.sumac.propertyresolver.TypeTransformer.isInt;
import static io.sumac.propertyresolver.TypeTransformer.isLong;
import static io.sumac.propertyresolver.TypeTransformer.isString;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import javax.sql.DataSource;

import io.sumac.propertyresolver.annotations.Property;
import io.sumac.propertyresolver.providers.CompositeProvider;
import io.sumac.propertyresolver.providers.Provider;
import io.sumac.propertyresolver.providers.cached.ClassPathResourceProvider;
import io.sumac.propertyresolver.providers.cached.SystemArgumentProvider;
import io.sumac.propertyresolver.providers.cached.refreshable.FileProvider;
import io.sumac.propertyresolver.providers.cached.refreshable.LambdaProvider;

public class PropertyResolver extends CompositeProvider {

	private BiConsumer<String, String> inspector;
	private Consumer<String> propertyNotFoundHandler;

	private UnaryOperator<String> transformer;

	private PropertyResolver(Provider... providers) {
		super(providers);
	}

	public static PropertyResolverBuilder registerProviders() {
		return new PropertyResolverBuilder();
	}

	public final <T> T to(Class<T> type) {
		try {
			validateConstructor(type);
			T clazz = construct(type);
			fillIn(clazz);
			return clazz;
		} catch (PropertyResolverException e) {
			throw e;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			throw PropertyResolverException.wrapCheckedReflectionExceptions(e);
		}
	}

	public final void fillIn(Object obj) {
		try {
			populateFields(obj);
			populateBySetters(obj);
		} catch (PropertyResolverException e) {
			throw e;
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			throw PropertyResolverException.wrapCheckedReflectionExceptions(e);
		}
	}

	private void validateConstructor(Class<?> type) {
		if (type.getDeclaredConstructors().length > 1) {
			throw PropertyResolverException.tooManyConstructors(type.getDeclaredConstructors().length);
		}
		for (Constructor<?> constructor : type.getDeclaredConstructors()) {
			for (Parameter parameter : constructor.getParameters()) {
				if (!parameter.isAnnotationPresent(Property.class)) {
					throw PropertyResolverException.constructorArgNotAnnotated(parameter);
				}
			}
		}
	}

	@Override
	public Optional<String> getString(String key) {
		Optional<String> value = super.getString(key);
		if (!value.isPresent()) {
			this.propertyNotFoundHandler.accept(key);
			return value;
		} else {
			return value.map(v -> {
				String transformedValue = transformer.apply(v);
				this.inspector.accept(key, transformedValue);
				return transformedValue;
			});
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T construct(Class<T> type)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		List<Object> properties = new ArrayList<>();
		Constructor<?> constructor = type.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Parameter[] parameters = constructor.getParameters();
		for (Parameter parameter : parameters) {
			Property property = parameter.getAnnotation(Property.class);
			if (isString(parameter.getType())) {
				properties.add(handleOptional(property, getString(property.name())));
			} else if (isLong(parameter.getType())) {
				properties.add(handleOptional(property, getLong(property.name())));
			} else if (isInt(parameter.getType())) {
				properties.add(handleOptional(property, getInt(property.name())));
			} else if (isDouble(parameter.getType())) {
				properties.add(handleOptional(property, getDouble(property.name())));
			} else if (isFloat(parameter.getType())) {
				properties.add(handleOptional(property, getFloat(property.name())));
			} else if (isBoolean(parameter.getType())) {
				properties.add(handleOptional(property, getBoolean(property.name())));
			} else {
				throw PropertyResolverException.unsupportedType(parameter);
			}
		}
		return (T) constructor.newInstance(properties.toArray());
	}

	private <T> void populateFields(T obj) throws IllegalAccessException {
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Property.class)) {
				setPrimitives(field, obj);
			}
		}
	}

	private void setPrimitives(Field field, Object obj) throws IllegalAccessException {
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);
		Property property = field.getAnnotation(Property.class);
		if (isString(field.getType())) {
			field.set(obj, handleOptional(property, getString(property.name())));
		} else if (isLong(field.getType())) {
			field.set(obj, handleOptional(property, getLong(property.name())));
		} else if (isInt(field.getType())) {
			field.set(obj, handleOptional(property, getInt(property.name())));
		} else if (isDouble(field.getType())) {
			field.set(obj, handleOptional(property, getDouble(property.name())));
		} else if (isFloat(field.getType())) {
			field.set(obj, handleOptional(property, getFloat(property.name())));
		} else if (isBoolean(field.getType())) {
			field.set(obj, handleOptional(property, getBoolean(property.name())));
		} else {
			throw PropertyResolverException.unsupportedType(field);
		}
		field.setAccessible(isAccessible);
	}

	private <T> void populateBySetters(T obj) throws IllegalAccessException, InvocationTargetException {
		for (Method method : obj.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Property.class)) {
				validateSetterMethod(method);
				setPrimitives(method, obj);
			}
		}
	}

	private void validateSetterMethod(Method method) {
		final int count = method.getParameterCount();
		if (count < 1) {
			throw PropertyResolverException.noSetterArgs(method);
		} else if (count > 1) {
			throw PropertyResolverException.tooManySetterArgs(method);
		}
	}

	private void setPrimitives(Method method, Object obj) throws IllegalAccessException, InvocationTargetException {
		boolean isAccessible = method.isAccessible();
		method.setAccessible(true);
		Property property = method.getAnnotation(Property.class);
		Parameter parameter = method.getParameters()[0];
		if (isString(parameter.getType())) {
			method.invoke(obj, handleOptional(property, getString(property.name())));
		} else if (isLong(parameter.getType())) {
			method.invoke(obj, handleOptional(property, getLong(property.name())));
		} else if (isInt(parameter.getType())) {
			method.invoke(obj, handleOptional(property, getInt(property.name())));
		} else if (isDouble(parameter.getType())) {
			method.invoke(obj, handleOptional(property, getDouble(property.name())));
		} else if (isFloat(parameter.getType())) {
			method.invoke(obj, handleOptional(property, getFloat(property.name())));
		} else if (isBoolean(parameter.getType())) {
			method.invoke(obj, handleOptional(property, getBoolean(property.name())));
		} else {
			throw PropertyResolverException.unsupportedType(parameter);
		}
		method.setAccessible(isAccessible);
	}

	private Object handleOptional(Property property, Optional<?> value) {
		if (property.optional()) {
			if (value.isPresent()) {
				return value.get();
			} else {
				return null;
			}
		} else {
			if (value.isPresent()) {
				return value.get();
			} else {
				throw PropertyResolverException.propertyNotFound(property.name());
			}
		}
	}

	public static class PropertyResolverBuilder {
		final List<Provider> providers = new ArrayList<>();

		private BiConsumer<String, String> inspector = (key, value) -> {
		};
		private Consumer<String> propertyNotFoundHandler = key -> {
		};

		private UnaryOperator<String> transformer = value -> value;

		private PropertyResolverBuilder() {

		}

		public PropertyResolverBuilder addSystemArguments() {
			providers.add(new SystemArgumentProvider());
			return this;
		}

		public PropertyResolverBuilder addClasspathPropertiesFile(String resource) {
			providers.add(new ClassPathResourceProvider(resource));
			return this;
		}

		public PropertyResolverBuilder addPropertiesFile(Path path) {
			providers.add(new FileProvider(path));
			return this;
		}

		public PropertyResolverBuilder addJdbcTable(DataSource source, String table) {
			providers.add(new io.sumac.propertyresolver.providers.cached.refreshable.JdbcProvider(source, table));
			return this;
		}

		public PropertyResolverBuilder addJdbcLookup(DataSource source, String table, String keyColumnName,
				String valueColumnName) {
			providers.add(new io.sumac.propertyresolver.providers.dynamic.JdbcProvider(source, table, keyColumnName,
					valueColumnName));
			return this;
		}

		public PropertyResolverBuilder useCustomInspector(BiConsumer<String, String> customInspector) {
			this.inspector = customInspector;
			return this;
		}

		public PropertyResolverBuilder useCustomPropertyNotFoundHandler(
				Consumer<String> customPropertyNotFoundHandler) {
			this.propertyNotFoundHandler = customPropertyNotFoundHandler;
			return this;
		}

		public PropertyResolverBuilder useCustomTransformer(UnaryOperator<String> customTransformer) {
			this.transformer = customTransformer;
			return this;
		}

		public PropertyResolverBuilder useCustomResolver(Supplier<Properties> supplier) {
			this.providers.add(new LambdaProvider(supplier));
			return this;
		}

		public PropertyResolverBuilder useProperties(Properties properties) {
			return useCustomResolver(() -> properties);
		}

		public PropertyResolver build() {
			PropertyResolver propertyResolver = new PropertyResolver(providers.toArray(new Provider[0]));
			propertyResolver.inspector = this.inspector;
			propertyResolver.propertyNotFoundHandler = this.propertyNotFoundHandler;
			propertyResolver.transformer = this.transformer;
			return propertyResolver;
		}
	}

}
