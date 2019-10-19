package io.sumac.propertyresolver.providers.cached.refreshable;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import io.sumac.propertyresolver.providers.Refreshable;
import io.sumac.propertyresolver.providers.cached.CachedPropertiesProvider;

public abstract class RefreshableProvider extends CachedPropertiesProvider implements Refreshable {

	private final Properties properties = new Properties();

	protected abstract Properties fetchAll();

	public void refresh() {
		synchronized (this) {
			properties.clear();
			properties.putAll(fetchAll());
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
