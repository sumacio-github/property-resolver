package io.sumac.propertyresolver.providers.cached;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class SystemArgumentProvider extends CachedPropertiesProvider {

	private final Properties properties;

	public SystemArgumentProvider() {
		this.properties = System.getProperties();
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
