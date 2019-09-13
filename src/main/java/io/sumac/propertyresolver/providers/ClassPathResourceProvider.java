package io.sumac.propertyresolver.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.Properties;

import io.sumac.propertyresolver.PropertyResolverException;

public class ClassPathResourceProvider extends RefreshableProvider implements Serializable {

	private static final long serialVersionUID = 993497248827481673L;
	private final String resourceName;

	public ClassPathResourceProvider(String resourceName) {
		this.resourceName = resourceName;
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		Properties props = new Properties();
		ClassLoader classLoader = PropertiesProvider.class.getClassLoader();
		try (final InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {
			if (Objects.isNull(inputStream)) {
				throw PropertyResolverException.errorReadingClasspathPropertyFile(resourceName);
			}
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingClasspathPropertyFile(resourceName, e);
		}
	}

}
