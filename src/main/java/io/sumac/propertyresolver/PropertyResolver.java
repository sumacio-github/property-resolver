package io.sumac.propertyresolver;

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
