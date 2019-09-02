package io.sumac.propertyresolver.providers;

import java.io.Serializable;
import java.util.Optional;
import java.util.Properties;

public abstract class RefreshableProvider extends PropertiesProvider implements Refreshable, Serializable {

	private static final long serialVersionUID = 532654152031686803L;
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
