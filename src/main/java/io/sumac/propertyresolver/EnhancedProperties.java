package io.sumac.propertyresolver;

import java.util.Optional;
import java.util.Properties;

/**
 * Basic implementation of static property reader {@code StaticPropertyReader}
 * 
 * @author ross
 *
 */
public class EnhancedProperties extends Properties {

	private static final long serialVersionUID = -8938167962916354262L;

	public EnhancedProperties() {
		super();
	}

	public EnhancedProperties(Properties properties) {
		super(properties);
	}

	public Optional<String> getString(String key) {
		if (this.containsKey(key)) {
			return Optional.of(this.getProperty(key));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Boolean> getBoolean(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(PropertyResolverUtils.toBoolean(opt.get()));
		}
	}

	public Optional<Integer> getInt(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(PropertyResolverUtils.toInt(opt.get()));
		}
	}

	public Optional<Long> getLong(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(PropertyResolverUtils.toLong(opt.get()));
		}
	}

	public Optional<Double> getDouble(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(PropertyResolverUtils.toDouble(opt.get()));
		}
	}

	public Optional<Float> getFloat(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(PropertyResolverUtils.toFloat(opt.get()));
		}
	}

}
