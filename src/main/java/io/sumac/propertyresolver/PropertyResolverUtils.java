package io.sumac.propertyresolver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for converting different classes to {@code Properties} classes
 * and includes other helpful utilities for internal logic
 * 
 * @author ross
 *
 */
public final class PropertyResolverUtils {
	private PropertyResolverUtils() {
		// hidden
	}

	/**
	 * Converts a {@code java.util.Properties} object to Properties. Note that
	 * {@code Properties} is a String based key/value pair map so the toString
	 * function will be invoked on both key and value during this conversion.
	 * 
	 * @param input object to convert to {@code Properties}
	 * @return the {@code Properties} instance
	 * @throws NullPointerException if input is null
	 */
	public static EnhancedProperties toProperties(@NotNull java.util.Properties input) {
		rejectIfNull(input);
		return new EnhancedProperties(input);
	}

	/**
	 * Converts a file located at the specified path to Properties.
	 * 
	 * @param filePath path file to be converted to {@code Properties}
	 * @return the {@code Properties} instance
	 * @throws NullPointerException if input is null
	 */
	public static EnhancedProperties toProperties(@NotNull Path filePath) {
		rejectIfNull(filePath);
		String path = filePath.toString();
		try (final InputStream inputStream = Files.newInputStream(Paths.get(path))) {
			return toProperties(inputStream);
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingPropertyFile(path, e);
		} catch (PropertyResolverException e) {
			throw PropertyResolverException.errorReadingPropertyFile(path, e);
		}
	}

	public static EnhancedProperties toProperties(@NotNull String resource) {
		rejectIfNull(resource);
		ClassLoader classLoader = PropertyResolverUtils.class.getClassLoader();
		try (final InputStream inputStream = classLoader.getResourceAsStream(resource)) {
			return toProperties(inputStream);
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingClasspathPropertyFile(resource, e);
		} catch (PropertyResolverException e) {
			throw PropertyResolverException.errorReadingClasspathPropertyFile(resource, e);
		}
	}

	public static EnhancedProperties toProperties(@NotNull InputStream inputStream) {
		rejectIfNull(inputStream);
		EnhancedProperties props = new EnhancedProperties();
		try {
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw PropertyResolverException.errorReadingInputStream(e);
		}
	}

	/**
	 * Converts a String key/value pair map to Properties.
	 * 
	 * @param properties map to be converted to {@code Properties}
	 * @return the {@code Properties} instance
	 * @throws NullPointerException if input is null
	 */
	public static EnhancedProperties toProperties(@NotNull Map<String, String> properties) {
		rejectIfNull(properties);
		EnhancedProperties enhancedProperties = new EnhancedProperties();
		enhancedProperties.putAll(properties);
		return enhancedProperties;
	}

	public static int toInt(String value) {
		rejectIfNull(value);
		return Integer.valueOf(value);
	}

	public static long toLong(String value) {
		rejectIfNull(value);
		return Long.valueOf(value);
	}

	public static double toDouble(String value) {
		rejectIfNull(value);
		return Double.valueOf(value);
	}

	public static float toFloat(String value) {
		rejectIfNull(value);
		return Float.valueOf(value);
	}

	public static boolean toBoolean(String value) {
		rejectIfNull(value);
		return Boolean.valueOf(value);
	}

	public static void rejectIfNull(Object value) {
		if (Objects.isNull(value)) {
			throw new NullPointerException();
		}
	}
}
