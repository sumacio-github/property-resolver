package io.sumac.propertyresolver.providers;

import java.util.Optional;

/** Resolves properties as primitive types */
public interface Provider {

	/** Reads a property and returns as a string, if found */
	Optional<String> getString(String key);

	/** Reads a property and returns as a boolean, if found */
	Optional<Boolean> getBoolean(String key);

	/** Reads a property and returns as an integer, if found */
	Optional<Integer> getInt(String key);

	/** Reads a property and returns as a long, if found */
	Optional<Long> getLong(String key);

	/** Reads a property and returns as a double, if found */
	Optional<Double> getDouble(String key);

	/** Reads a property and returns as a float, if found */
	Optional<Float> getFloat(String key);

}
