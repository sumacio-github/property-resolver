package io.sumac.propertyresolver.providers;

import java.util.Properties;

public class SystemArgumentProvider extends RefreshableProvider {

	public SystemArgumentProvider() {
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		return System.getProperties();
	}

}
