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

	@Override
	public Optional<Boolean> getBoolean(String key) {
		for (Provider provider : providers) {
			final var value = provider.getBoolean(key);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Integer> getInt(String key) {
		for (Provider provider : providers) {
			final var value = provider.getInt(key);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Long> getLong(String key) {
		for (Provider provider : providers) {
			final var value = provider.getLong(key);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Double> getDouble(String key) {
		for (Provider provider : providers) {
			final var value = provider.getDouble(key);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<Float> getFloat(String key) {
		for (Provider provider : providers) {
			final var value = provider.getFloat(key);
			if (!value.isEmpty()) {
				return value;
			}
		}
		return Optional.empty();
	}
}