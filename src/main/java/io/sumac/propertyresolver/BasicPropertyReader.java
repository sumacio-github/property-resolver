package io.sumac.propertyresolver;

import java.util.Optional;

/**
 * Base interface for reading properties.
 * 
 * @author ross
 *
 */
@FunctionalInterface
public interface BasicPropertyReader {

	/**
	 * Read a string property that may or may not exist
	 * 
	 * @param key the property key
	 * @return the property value as optional
	 */
	Optional<String> getProperty(String key);
}
