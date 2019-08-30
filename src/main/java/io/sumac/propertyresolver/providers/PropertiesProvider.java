package io.sumac.propertyresolver.providers;

import java.util.Optional;

import io.sumac.propertyresolver.TypeTransformer;

public abstract class PropertiesProvider implements Provider {

	@Override
	public Optional<Boolean> getBoolean(String key) {
		var opt = getString(key);
		if (opt.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(TypeTransformer.toBoolean(opt.get()));
		}
	}

	@Override
	public Optional<Integer> getInt(String key) {
		var opt = getString(key);
		if (opt.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(TypeTransformer.toInt(opt.get()));
		}
	}

	@Override
	public Optional<Long> getLong(String key) {
		var opt = getString(key);
		if (opt.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(TypeTransformer.toLong(opt.get()));
		}
	}

	@Override
	public Optional<Double> getDouble(String key) {
		var opt = getString(key);
		if (opt.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(TypeTransformer.toDouble(opt.get()));
		}
	}

	@Override
	public Optional<Float> getFloat(String key) {
		var opt = getString(key);
		if (opt.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(TypeTransformer.toFloat(opt.get()));
		}
	}

}
