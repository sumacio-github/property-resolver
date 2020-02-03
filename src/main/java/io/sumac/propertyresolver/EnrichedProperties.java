package io.sumac.propertyresolver;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	private final Environment environment = new Environment(this);

	private Function<String, String> decryptor = (s) -> s; // placeholder

	public EnrichedProperties() {
		super();
	}

	public EnrichedProperties(Properties properties) {
		super(properties);
	}

	public Environment getEnvironment() {
		return this.environment;
	}

	public String getString(String key) {
		if (this.containsKey(key)) {
			return decryptor.apply(this.getProperty(key));
		} else {
			return null;
		}
	}

	public Optional<String> getStringOptional(String key) {
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

	public String[] getStrings(String key, String splitter) {
		if (this.containsKey(key)) {
			return decryptor.apply(this.getProperty(key)).split(splitter);
		} else {
			return new String[0];
		}
	}

	public String[] getStringsRequired(String key, String splitter) {
		rejectIfRequiredKeyNotFound(key);
		return decryptor.apply(this.getProperty(key)).split(splitter);
	}

	public Boolean getBoolean(String key) {
		if (this.containsKey(key)) {
			return toBoolean(decryptor.apply(this.getProperty(key)));
		} else {
			return null;
		}
	}

	public Optional<Boolean> getBooleanOptional(String key) {
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

	public boolean[] getBooleans(String key, String splitter) {
		if (this.containsKey(key)) {
			return toBooleans(decryptor.apply(this.getProperty(key)).split(splitter));
		} else {
			return new boolean[0];
		}
	}

	public boolean[] getBooleansRequired(String key, String splitter) {
		rejectIfRequiredKeyNotFound(key);
		return toBooleans(decryptor.apply(this.getProperty(key)).split(splitter));
	}

	public Integer getInt(String key) {
		if (this.containsKey(key)) {
			return toInt(decryptor.apply(this.getProperty(key)));
		} else {
			return null;
		}
	}

	public Optional<Integer> getIntOptional(String key) {
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

	public int[] getInts(String key, String splitter) {
		if (this.containsKey(key)) {
			return toInts(decryptor.apply(this.getProperty(key)).split(splitter));
		} else {
			return new int[0];
		}
	}

	public int[] getIntsRequired(String key, String splitter) {
		rejectIfRequiredKeyNotFound(key);
		return toInts(decryptor.apply(this.getProperty(key)).split(splitter));
	}

	public Long getLong(String key) {
		if (this.containsKey(key)) {
			return toLong(decryptor.apply(this.getProperty(key)));
		} else {
			return null;
		}
	}

	public Optional<Long> getLongOptional(String key) {
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

	public long[] getLongs(String key, String splitter) {
		if (this.containsKey(key)) {
			return toLongs(decryptor.apply(this.getProperty(key)).split(splitter));
		} else {
			return new long[0];
		}
	}

	public long[] getLongsRequired(String key, String splitter) {
		rejectIfRequiredKeyNotFound(key);
		return toLongs(decryptor.apply(this.getProperty(key)).split(splitter));
	}

	public Double getDouble(String key) {
		if (this.containsKey(key)) {
			return toDouble(decryptor.apply(this.getProperty(key)));
		} else {
			return null;
		}
	}

	public Optional<Double> getDoubleOptional(String key) {
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

	public double[] getDoubles(String key, String splitter) {
		if (this.containsKey(key)) {
			return toDoubles(decryptor.apply(this.getProperty(key)).split(splitter));
		} else {
			return new double[0];
		}
	}

	public double[] getDoublesRequired(String key, String splitter) {
		rejectIfRequiredKeyNotFound(key);
		return toDoubles(decryptor.apply(this.getProperty(key)).split(splitter));
	}

	public Float getFloat(String key) {
		if (this.containsKey(key)) {
			return toFloat(decryptor.apply(this.getProperty(key)));
		} else {
			return null;
		}
	}

	public Optional<Float> getFloatOptional(String key) {
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

	public float[] getFloats(String key, String splitter) {
		if (this.containsKey(key)) {
			return toFloats(decryptor.apply(this.getProperty(key)).split(splitter));
		} else {
			return new float[0];
		}
	}

	public float[] getFloatsRequired(String key, String splitter) {
		rejectIfRequiredKeyNotFound(key);
		return toFloats(decryptor.apply(this.getProperty(key)).split(splitter));
	}

	public Date getDate(String key, String pattern) {
		if (this.containsKey(key)) {
			return toDate(decryptor.apply(this.getProperty(key)), pattern);
		} else {
			return null;
		}
	}

	public Optional<Date> getDateOptional(String key, String pattern) {
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

	public Date[] getDates(String key, String splitter, String pattern) {
		if (this.containsKey(key)) {
			return toDates(decryptor.apply(this.getProperty(key)).split(splitter), pattern);
		} else {
			return new Date[0];
		}
	}

	public Date[] getDatesRequired(String key, String splitter, String pattern) {
		rejectIfRequiredKeyNotFound(key);
		return toDates(decryptor.apply(this.getProperty(key)).split(splitter), pattern);
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

	public String toPropertiesString() {
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
		putAllIfAbsent(System.getProperties());
	}

	public void loadFromEnvIfAbsent() {
		Properties env = new Properties();
		env.putAll(System.getenv());
		putAllIfAbsent(env);
	}

	public void putAllIfAbsent(Properties props) {
		props.forEach((k, v) -> {
			if (!this.contains(k)) {
				this.put(k, v);
			}
		});
		this.putAll(props);
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

	private void rejectIfRequiredKeyNotFound(String key) {
		if (!this.contains(key)) {
			throw PropertyResolverException.propertyNotFound(key);
		}
	}

	private static int toInt(String value) {
		rejectIfNull(value);
		return Integer.valueOf(value);
	}

	private static int[] toInts(String[] values) {
		rejectIfNull(values);
		int[] output = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = toInt(values[i]);
		}
		return output;
	}

	private static long toLong(String value) {
		rejectIfNull(value);
		return Long.valueOf(value);
	}

	private static long[] toLongs(String[] values) {
		rejectIfNull(values);
		long[] output = new long[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = toLong(values[i]);
		}
		return output;
	}

	private static double toDouble(String value) {
		rejectIfNull(value);
		return Double.valueOf(value);
	}

	private static double[] toDoubles(String[] values) {
		rejectIfNull(values);
		double[] output = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = toDouble(values[i]);
		}
		return output;
	}

	private static float toFloat(String value) {
		rejectIfNull(value);
		return Float.valueOf(value);
	}

	private static float[] toFloats(String[] values) {
		rejectIfNull(values);
		float[] output = new float[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = toFloat(values[i]);
		}
		return output;
	}

	private static boolean toBoolean(String value) {
		rejectIfNull(value);
		return Boolean.valueOf(value);
	}

	private static boolean[] toBooleans(String[] values) {
		rejectIfNull(values);
		boolean[] output = new boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = toBoolean(values[i]);
		}
		return output;
	}

	private static Date toDate(String value, String pattern) {
		rejectIfNull(pattern);
		rejectIfNull(value);
		return Date.from(ZonedDateTime.parse(value, DateTimeFormatter.ofPattern(pattern)).toInstant());
	}

	private static Date[] toDates(String[] values, String pattern) {
		rejectIfNull(values);
		Date[] output = new Date[values.length];
		for (int i = 0; i < values.length; i++) {
			output[i] = toDate(values[i], pattern);
		}
		return output;
	}

	private static void rejectIfNull(Object value) {
		if (Objects.isNull(value)) {
			throw new NullPointerException();
		}
	}

	public static class Environment {

		private static final String DEFAULT_APP_NAME_KEY = "environment.appName";
		private static final String DEFAULT_ACTIVE_PROFILE_KEY = "environment.activeProfile";
		private static final String DEFAULT_APP_VERSION_KEY = "environment.appVersion";

		private String appNameKey = DEFAULT_APP_NAME_KEY;
		private String activeProfileKey = DEFAULT_ACTIVE_PROFILE_KEY;
		private String appVersionKey = DEFAULT_APP_VERSION_KEY;

		private final EnrichedProperties properties;

		private Environment(EnrichedProperties properties) {
			this.properties = properties;
		}

		public String getAppNameKey() {
			return appNameKey;
		}

		public String getActiveProfileKey() {
			return activeProfileKey;
		}

		public String getAppVersionKey() {
			return appVersionKey;
		}

		public void setAppNameKey(String appNameKey) {
			rejectIfNull(appNameKey);
			this.appNameKey = appNameKey;
		}

		public void setActiveProfileKey(String activeProfileKey) {
			rejectIfNull(activeProfileKey);
			this.activeProfileKey = activeProfileKey;
		}

		public void setAppVersionKey(String appVersionKey) {
			rejectIfNull(appVersionKey);
			this.appVersionKey = appVersionKey;
		}

		public String getAppName() {
			return properties.getStringRequired(appNameKey);
		}

		public String getActiveProfile() {
			return properties.getStringRequired(activeProfileKey);
		}

		public String getAppVersion() {
			return properties.getStringRequired(appVersionKey);
		}
	}

}
