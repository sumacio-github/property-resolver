package io.sumac.propertyresolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class EnhancedProperties extends Properties {

	private static final long serialVersionUID = -8938167962916354262L;

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final YAMLMapper YAML_MAPPER = new YAMLMapper();
	private static final JavaPropsMapper PROPS_MAPPER = new JavaPropsMapper();
	private Function<String, String> decryptor = (s) -> s;

	public EnhancedProperties() {
		super();
	}

	public EnhancedProperties(Properties properties) {
		super(properties);
	}

	public Optional<String> getString(String key) {
		if (this.containsKey(key)) {
			return Optional.of(decryptor.apply(this.getProperty(key)));
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

	public String toYamlString() throws JsonProcessingException, IOException {
		return YAML_MAPPER.writeValueAsString(toJsonNode());
	}

	public String toJsonString() throws JsonProcessingException, IOException {
		return toJsonString(false);
	}

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

	public EnhancedProperties filterByRegex(String regex) {
		Pattern pattern = Pattern.compile(regex);
		EnhancedProperties result = new EnhancedProperties();
		this.forEach((k, v) -> {
			Matcher matcher = pattern.matcher(k.toString());
			if (matcher.matches()) {
				result.put(k, v);
			}
		});
		return result;
	}

	public EnhancedProperties getChildProperties(String parentPropertyKey) {
		EnhancedProperties result = new EnhancedProperties();
		this.forEach((k, v) -> {
			if (k instanceof String && k.toString().startsWith(parentPropertyKey)) {
				result.put(k.toString().replace(parentPropertyKey + ".", ""), v);
			}
		});
		return result;
	}

}
