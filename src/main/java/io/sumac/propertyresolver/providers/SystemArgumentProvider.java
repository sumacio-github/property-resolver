package io.sumac.propertyresolver.providers;

import java.io.Serializable;
import java.util.Properties;

public class SystemArgumentProvider extends RefreshableProvider implements Serializable {

	private static final long serialVersionUID = -6640799661839259572L;

	public SystemArgumentProvider() {
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		return System.getProperties();
	}

}
