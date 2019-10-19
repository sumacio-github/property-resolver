package io.sumac.propertyresolver.providers.cached;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import io.sumac.propertyresolver.PropertyResolverException;
import io.sumac.propertyresolver.providers.PropertiesProvider;

public class ClassPathResourceProvider extends CachedPropertiesProvider {

	private final Properties properties;

	public ClassPathResourceProvider(String resourceName) {
		this.properties = new Properties();
		ClassLoader classLoader = PropertiesProvider.class.getClassLoader();
		try (final InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {
			if (Objects.isNull(inputStream)) {
				throw PropertyResolverException.errorReadingClasspathPropertyFile(resourceName);
			}
			properties.load(inputStream);
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingClasspathPropertyFile(resourceName, e);
		}
	}

	@Override
	public Optional<String> getString(String key) {
		return Optional.ofNullable(properties.getProperty(key));
	}

	@Override
	public Set<String> getPropertyNames() {
		return properties.stringPropertyNames();
	}

}
