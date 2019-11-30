package io.sumac.propertyresolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

/**
 * Extension of {@code java.util.Properties} that adds some additional
 * convenience methods.
 * 
 * @author ross
 *
 */
public class EnrichedProperties extends Properties {

	private static final long serialVersionUID = -8938167962916354262L;

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final YAMLMapper YAML_MAPPER = new YAMLMapper();
	private static final JavaPropsMapper PROPS_MAPPER = new JavaPropsMapper();

	private Function<String, String> decryptor = (s) -> s; // placeholder

	/**
	 * Create an empty property list
	 */
	public EnrichedProperties() {
		super();
	}

	/**
	 * Create a property list with the provided defaults
	 * 
	 * @param properties default properties
	 */
	public EnrichedProperties(Properties properties) {
		super(properties);
	}

	/**
	 * Same as {@link java.util.Properties#getProperty(String)} except the result is
	 * optionally returned as a {@code String}
	 * 
	 * @param key the property key
	 * @return Optional of String if found or Optional.empty() if not found
	 */
	public Optional<String> getString(String key) {
		if (this.containsKey(key)) {
			return Optional.of(decryptor.apply(this.getProperty(key)));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Same as {@link java.util.Properties#getProperty(String)} except the result is
	 * optionally returned as a {@code Boolean}
	 * 
	 * @param key the property key
	 * @return Optional of Boolean if found or Optional.empty() if not found
	 */
	public Optional<Boolean> getBoolean(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toBoolean(opt.get()));
		}
	}

	/**
	 * Same as {@link java.util.Properties#getProperty(String)} except the result is
	 * optionally returned as an {@code Integer}
	 * 
	 * @param key the property key
	 * @return Optional of Integer if found or Optional.empty() if not found
	 */
	public Optional<Integer> getInt(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toInt(opt.get()));
		}
	}

	/**
	 * Same as {@link java.util.Properties#getProperty(String)} except the result is
	 * optionally returned as a {@code Long}
	 * 
	 * @param key the property key
	 * @return Optional of Long if found or Optional.empty() if not found
	 */
	public Optional<Long> getLong(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toLong(opt.get()));
		}
	}

	/**
	 * Same as {@link java.util.Properties#getProperty(String)} except the result is
	 * optionally returned as a {@code Double}
	 * 
	 * @param key the property key
	 * @return Optional of Double if found or Optional.empty() if not found
	 */
	public Optional<Double> getDouble(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toDouble(opt.get()));
		}
	}

	/**
	 * Same as {@link java.util.Properties#getProperty(String)} except the result is
	 * optionally returned as a {@code Float}
	 * 
	 * @param key the property key
	 * @return Optional of Float if found or Optional.empty() if not found
	 */
	public Optional<Float> getFloat(String key) {
		Optional<String> opt = getString(key);
		if (!opt.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.of(toFloat(opt.get()));
		}
	}

	/**
	 * This is a specialized toString method that returns a string representation of
	 * this object formatted as a yaml file
	 * 
	 * @return a yaml formatted string representation of this object
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String toYamlString() throws JsonProcessingException, IOException {
		return YAML_MAPPER.writeValueAsString(toJsonNode());
	}

	public String toJsonString() throws JsonProcessingException, IOException {
		return toJsonString(false);
	}

	/**
	 * @param pretty
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String toJsonString(boolean pretty) throws JsonProcessingException, IOException {
		if (pretty) {
			return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(toJsonNode());
		} else {
			return JSON_MAPPER.writeValueAsString(toJsonNode());
		}
	}

	public String toPropertiesString() throws JsonProcessingException, IOException {
		boolean first = true;
		StringBuilder builder = new StringBuilder();
		List<String> keys = new ArrayList<>(this.stringPropertyNames());
		Collections.sort(keys);
		for (String key : keys) {
			if (first) {
				first = false;
			} else {
				builder.append('\n');
			}
			builder.append(key).append("=").append(this.getProperty(key));
		}
		return builder.toString();
	}

	public JsonNode toJsonNode() throws IOException {
		return PROPS_MAPPER.readPropertiesAs(this, JsonNode.class);
	}

	public void loadFromJsonNode(JsonNode input) throws IOException {
		this.putAll(PROPS_MAPPER.writeValueAsProperties(input));
	}

	public void loadFromJsonString(String input) throws IOException {
		loadFromJsonNode(JSON_MAPPER.readTree(input));
	}

	public void loadFromYamlString(String input) throws IOException {
		loadFromJsonNode(YAML_MAPPER.readTree(input));
	}

	public void loadFromSystemArgs() {
		this.putAll(System.getProperties());
	}

	public void loadFromEnv() {
		this.putAll(System.getenv());
	}

	public void loadFromSystemArgsIfAbsent() {
		this.putAll(System.getProperties());
	}

	public void loadFromEnvIfAbsent() {
		this.putAll(System.getenv());
	}

	public void setDecryptor(Function<String, String> decryptor) {
		this.decryptor = decryptor;
	}

	public EnrichedProperties filterByRegex(String regex) {
		Pattern pattern = Pattern.compile(regex);
		EnrichedProperties result = new EnrichedProperties();
		this.forEach((k, v) -> {
			Matcher matcher = pattern.matcher(k.toString());
			if (matcher.matches()) {
				result.put(k, v);
			}
		});
		return result;
	}

	public EnrichedProperties getChildProperties(String parentPropertyKey) {
		EnrichedProperties result = new EnrichedProperties();
		this.forEach((k, v) -> {
			if (k instanceof String && k.toString().startsWith(parentPropertyKey)) {
				result.put(k.toString().replace(parentPropertyKey + ".", ""), v);
			}
		});
		return result;
	}

	private static int toInt(String value) {
		rejectIfNull(value);
		return Integer.valueOf(value);
	}

	private static long toLong(String value) {
		rejectIfNull(value);
		return Long.valueOf(value);
	}

	private static double toDouble(String value) {
		rejectIfNull(value);
		return Double.valueOf(value);
	}

	private static float toFloat(String value) {
		rejectIfNull(value);
		return Float.valueOf(value);
	}

	private static boolean toBoolean(String value) {
		rejectIfNull(value);
		return Boolean.valueOf(value);
	}

	private static void rejectIfNull(Object value) {
		if (Objects.isNull(value)) {
			throw new NullPointerException();
		}
	}

}
