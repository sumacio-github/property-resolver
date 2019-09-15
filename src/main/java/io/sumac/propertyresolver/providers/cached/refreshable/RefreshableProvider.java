package io.sumac.propertyresolver.providers.cached.refreshable;

import java.util.Optional;
import java.util.Properties;

import io.sumac.propertyresolver.providers.PropertiesProvider;
import io.sumac.propertyresolver.providers.Refreshable;

public abstract class RefreshableProvider extends PropertiesProvider implements Refreshable {

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
}
