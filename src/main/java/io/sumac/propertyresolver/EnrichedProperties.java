package io.sumac.propertyresolver;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
	private static final XmlMapper XML_MAPPER = new XmlMapper();

	private Function<String, String> decryptor = (s) -> s; // placeholder

	public EnrichedProperties() {
		super();
	}

	public EnrichedProperties(Properties properties) {
		super(properties);
	}

	public Optional<String> getString(String key) {
		if (this.containsKey(key)) {
			return Optional.of(decryptor.apply(this.getProperty(key)));
		} else {
			return Optional.empty();
		}
	}

	public String getStringRequired(String key) {
		rejectIfRequiredKeyNotFound(key);
		return decryptor.apply(this.getProperty(key));
	}

	public Optional<Boolean> getBoolean(String key) {
		if (this.containsKey(key)) {
			return Optional.of(toBoolean(decryptor.apply(this.getProperty(key))));
		} else {
			return Optional.empty();
		}
	}

	public boolean getBooleanRequired(String key) {
		rejectIfRequiredKeyNotFound(key);
		return toBoolean(decryptor.apply(this.getProperty(key)));
	}

	public Optional<Integer> getInt(String key) {
		if (this.containsKey(key)) {
			return Optional.of(toInt(decryptor.apply(this.getProperty(key))));
		} else {
			return Optional.empty();
		}
	}

	public int getIntRequired(String key) {
		rejectIfRequiredKeyNotFound(key);
		return toInt(decryptor.apply(this.getProperty(key)));
	}

	public Optional<Long> getLong(String key) {
		if (this.containsKey(key)) {
			return Optional.of(toLong(decryptor.apply(this.getProperty(key))));
		} else {
			return Optional.empty();
		}
	}

	public long getLongRequired(String key) {
		rejectIfRequiredKeyNotFound(key);
		return toLong(decryptor.apply(this.getProperty(key)));
	}

	public Optional<Double> getDouble(String key) {
		if (this.containsKey(key)) {
			return Optional.of(toDouble(decryptor.apply(this.getProperty(key))));
		} else {
			return Optional.empty();
		}
	}

	public double getDoubleRequired(String key) {
		rejectIfRequiredKeyNotFound(key);
		return toDouble(decryptor.apply(this.getProperty(key)));
	}

	public Optional<Float> getFloat(String key) {
		if (this.containsKey(key)) {
			return Optional.of(toFloat(decryptor.apply(this.getProperty(key))));
		} else {
			return Optional.empty();
		}
	}

	public float getFloatRequired(String key) {
		rejectIfRequiredKeyNotFound(key);
		return toFloat(decryptor.apply(this.getProperty(key)));
	}

	public Optional<Date> getDate(String key, String pattern) {
		if (this.containsKey(key)) {
			return Optional.of(toDate(decryptor.apply(this.getProperty(key)), pattern));
		} else {
			return Optional.empty();
		}
	}

	public Date getDateRequired(String key, String pattern) {
		rejectIfRequiredKeyNotFound(key);
		return toDate(decryptor.apply(this.getProperty(key)), pattern);
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

	public void loadFromObject(Object input) throws IOException {
		loadFromJsonNode(JSON_MAPPER.valueToTree(input));
	}

	public void loadFromXmlString(String input, Class<?> schema) throws IOException {
		loadFromObject(XML_MAPPER.readValue(input, schema));
	}

	public void show() {
		TreeSet<String> sortedKeys = new TreeSet<>(this.stringPropertyNames());
		sortedKeys.forEach(k -> System.out.println(k + "=" + this.getProperty(k)));
	}

	public void loadFromSystemArgs() {
		this.putAll(System.getProperties());
	}

	public void loadFromEnv() {
		this.putAll(System.getenv());
	}

	public void loadFromSystemArgsIfAbsent() {
		putAllIfAbsent(System.getProperties());
	}

	public void loadFromEnvIfAbsent() {
		Properties env = new Properties();
		env.putAll(System.getenv());
		putAllIfAbsent(env);
	}

	public void loadFromSource(Supplier<Properties> source) {
		this.putAll(source.get());
	}

	public static EnrichedProperties toEnriched(Properties props) {
		return new EnrichedProperties(props);
	}

	public void putAllIfAbsent(Properties props) {
		props.forEach((k, v) -> {
			if (!this.contains(k)) {
				this.put(k, v);
			}
		});
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

	public EnrichedProperties filterByStartsWith(String keyStartsWith) {
		EnrichedProperties result = new EnrichedProperties();
		this.forEach((k, v) -> {
			if (k instanceof String && k.toString().startsWith(keyStartsWith)) {
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

	private void rejectIfRequiredKeyNotFound(String key) {
		if (!this.containsKey(key)) {
			throw PropertyResolverException.propertyNotFound(key);
		}
	}

	private static int toInt(String value) {
		rejectIfNull(value);
		return Integer.parseInt(value);
	}

	private static long toLong(String value) {
		rejectIfNull(value);
		return Long.parseLong(value);
	}

	private static double toDouble(String value) {
		rejectIfNull(value);
		return Double.parseDouble(value);
	}

	private static float toFloat(String value) {
		rejectIfNull(value);
		return Float.parseFloat(value);
	}

	private static boolean toBoolean(String value) {
		rejectIfNull(value);
		return Boolean.parseBoolean(value);
	}

	private static Date toDate(String value, String pattern) {
		rejectIfNull(pattern);
		rejectIfNull(value);
		return Date.from(ZonedDateTime.parse(value, DateTimeFormatter.ofPattern(pattern)).toInstant());
	}

	private static void rejectIfNull(Object value) {
		if (Objects.isNull(value)) {
			throw new NullPointerException();
		}
	}

}
