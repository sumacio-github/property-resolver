package io.sumac.propertyresolver;

import static io.sumac.propertyresolver.TypeTransformer.isBoolean;
import static io.sumac.propertyresolver.TypeTransformer.isDouble;
import static io.sumac.propertyresolver.TypeTransformer.isFloat;
import static io.sumac.propertyresolver.TypeTransformer.isInt;
import static io.sumac.propertyresolver.TypeTransformer.isLong;
import static io.sumac.propertyresolver.TypeTransformer.isString;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import io.sumac.propertyresolver.annotations.Property;
import io.sumac.propertyresolver.functions.SerializableBiConsumer;
import io.sumac.propertyresolver.functions.SerializableConsumer;
import io.sumac.propertyresolver.functions.SerializableUnaryOperator;
import io.sumac.propertyresolver.providers.ClassPathResourceProvider;
import io.sumac.propertyresolver.providers.CompositeProvider;
import io.sumac.propertyresolver.providers.FileProvider;
import io.sumac.propertyresolver.providers.JdbcProvider;
import io.sumac.propertyresolver.providers.RefreshableProvider;
import io.sumac.propertyresolver.providers.SystemArgumentProvider;

public class PropertyResolver extends CompositeProvider implements Serializable {

	private static final long serialVersionUID = -5491745026934786712L;

	private SerializableBiConsumer inspector;
	private SerializableConsumer propertyNotFoundHandler;

	private SerializableUnaryOperator transformer;

	private PropertyResolver(RefreshableProvider... providers) {
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
		var value = super.getString(key);
		if (value.isEmpty()) {
			this.propertyNotFoundHandler.accept(key);
			return value;
		} else {
			return value.map(v -> {
				var transformedValue = transformer.apply(v);
				this.inspector.accept(key, transformedValue);
				return transformedValue;
			});
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T construct(Class<T> type)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		var properties = new ArrayList<Object>();
		var constructor = type.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		var parameters = constructor.getParameters();
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
		var isAccessible = field.canAccess(obj);
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
		var isAccessible = method.canAccess(obj);
		method.setAccessible(true);
		var property = method.getAnnotation(Property.class);
		var parameter = method.getParameters()[0];
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
		final List<RefreshableProvider> providers = new ArrayList<>();

		private SerializableBiConsumer inspector = (key, value) -> {
		};
		private SerializableConsumer propertyNotFoundHandler = key -> {
		};

		private SerializableUnaryOperator transformer = value -> value;

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
			providers.add(new JdbcProvider(source, table));
			return this;
		}

		public PropertyResolverBuilder useCustomInspector(SerializableBiConsumer customInspector) {
			this.inspector = customInspector;
			return this;
		}

		public PropertyResolverBuilder useCustomPropertyNotFoundHandler(
				SerializableConsumer customPropertyNotFoundHandler) {
			this.propertyNotFoundHandler = customPropertyNotFoundHandler;
			return this;
		}

		public PropertyResolverBuilder useCustomTransformer(SerializableUnaryOperator customTransformer) {
			this.transformer = customTransformer;
			return this;
		}

		public PropertyResolver build() {
			var propertyResolver = new PropertyResolver(providers.toArray(new RefreshableProvider[0]));
			propertyResolver.inspector = this.inspector;
			propertyResolver.propertyNotFoundHandler = this.propertyNotFoundHandler;
			propertyResolver.transformer = this.transformer;
			return propertyResolver;
		}
	}

}
