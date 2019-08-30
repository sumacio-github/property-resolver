package io.sumac.propertyresolver.providers;

import java.util.Properties;

public class EnvironmentVariableProvider extends RefreshableProvider {

	public EnvironmentVariableProvider() {
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		var properties = new Properties();
		properties.putAll(System.getenv());
		return properties;
	}

}
