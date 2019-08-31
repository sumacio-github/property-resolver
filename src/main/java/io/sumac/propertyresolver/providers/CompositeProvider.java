package io.sumac.propertyresolver.providers;

import java.util.Optional;

public abstract class CompositeProvider extends PropertiesProvider implements Refreshable {

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
			final var value = provider.getString(key);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return Optional.empty();
	}

}