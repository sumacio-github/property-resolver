package io.sumac.propertyresolver.providers;

import java.io.Serializable;
import java.util.Optional;

public abstract class CompositeProvider extends PropertiesProvider implements Refreshable, Serializable {

	private static final long serialVersionUID = 5107785980057380240L;
	private RefreshableProvider[] providers;

	public CompositeProvider(RefreshableProvider... providers) {
		this.providers = providers;
	}

	@Override
	public void refresh() {
		for (RefreshableProvider provider : providers) {
			provider.refresh();
		}
	}

	@Override
	public Optional<String> getString(String key) {
		for (Provider provider : providers) {
			final Optional<String> value = provider.getString(key);
			if (value.isPresent()) {
				return value;
			}
		}
		return Optional.empty();
	}

}