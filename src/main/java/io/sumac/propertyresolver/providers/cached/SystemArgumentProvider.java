package io.sumac.propertyresolver.providers.cached;

import java.util.Optional;
import java.util.Properties;

import io.sumac.propertyresolver.providers.PropertiesProvider;

public class SystemArgumentProvider extends PropertiesProvider {

	private final Properties properties;

	public SystemArgumentProvider() {
		this.properties = System.getProperties();
	}

	@Override
	public Optional<String> getString(String key) {
		return Optional.ofNullable(properties.getProperty(key));
	}

}
