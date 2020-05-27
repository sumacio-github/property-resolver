package io.sumac.propertyresolver;

import io.sumac.propertyresolver.utility.IOThrowingSupplier;
import io.sumac.propertyresolver.utility.PreCondition;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extension of {@code java.util.Properties} that adds some additional
 * convenience methods.
 *
 * @author ross
 */
public class EnrichedProperties extends Properties {

    private Function<String, String> decryptor = (s) -> s; // placeholder
    private Function<String, String> interpolator = (s) -> InterpolationHelper.interpolate(this, s);

    public EnrichedProperties() {
        super();
    }

    public EnrichedProperties(Properties properties) {
        super();
        putAll(properties);
    }

    public Optional<String> getString(String key) {
        if (this.containsKey(key)) {
            return Optional.of(decryptor.apply(interpolate(this.getProperty(key))));
        } else {
            return Optional.empty();
        }
    }

    public String getStringRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return decryptor.apply(interpolate(this.getProperty(key)));
    }

    public Optional<Boolean> getBoolean(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toBoolean(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    public boolean getBooleanRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toBoolean(decryptor.apply(interpolate(this.getProperty(key))));
    }

    public Optional<Integer> getInt(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toInt(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    public int getIntRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toInt(decryptor.apply(interpolate(this.getProperty(key))));
    }

    public Optional<Long> getLong(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toLong(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    public long getLongRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toLong(decryptor.apply(interpolate(this.getProperty(key))));
    }

    public Optional<Double> getDouble(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toDouble(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    public double getDoubleRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toDouble(decryptor.apply(interpolate(this.getProperty(key))));
    }

    public Optional<Float> getFloat(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toFloat(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    public float getFloatRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toFloat(decryptor.apply(interpolate(this.getProperty(key))));
    }

    public Optional<Date> getDate(String key, String pattern) {
        if (this.containsKey(key)) {
            return Optional.of(toDate(decryptor.apply(interpolate(this.getProperty(key))), pattern));
        } else {
            return Optional.empty();
        }
    }

    public Date getDateRequired(String key, String pattern) {
        rejectIfRequiredKeyNotFound(key);
        return toDate(decryptor.apply(interpolate(this.getProperty(key))), pattern);
    }

    public void show() {
        TreeSet<String> sortedKeys = new TreeSet<>(this.stringPropertyNames());
        sortedKeys.forEach(k -> System.out.println(k + "=" + interpolate(this.getProperty(k))));
    }

    public void show(IOThrowingSupplier<String> customFormatter) {
        try {
            System.out.println(interpolate(PreCondition.Result.notNull(customFormatter.get())));
        } catch (IOException e) {
            throw PropertyResolverException.from(e);
        }
    }

    public void loadFromSource(IOThrowingSupplier<Properties> source) throws IOException {
        loadFromSource(Collections.singletonList(source));
    }

    public void loadFromSource(List<IOThrowingSupplier<Properties>> sources) throws IOException {
        for (IOThrowingSupplier<Properties> source : sources) {
            putAll(source.get());
        }
    }

    public void setDecryptor(Function<String, String> decryptor) {
        this.decryptor = decryptor;
    }

    public void setInterpolator(Function<String, String> interpolator) {
        this.interpolator = interpolator;
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
            if (k instanceof String && k.toString().startsWith(keyStartsWith + ".")) {
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

    public String interpolate(String text) {
        return interpolator.apply(text);
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

    static class InterpolationHelper {

        private static final String STARTS_WITH = "${";
        private static final String ENDS_WITH = "}";

        static String interpolate(Properties properties, String text) {
            int startindex = text.indexOf(STARTS_WITH);
            if (startindex == -1) {
                return text;
            }
            int start = startindex + STARTS_WITH.length();

            int endindex = text.substring(start).indexOf(ENDS_WITH);
            if (endindex == -1) {
                return text;
            }
            int end = endindex + start;
            String candidate = text.substring(start, end);
            if (properties.containsKey(candidate)) {
                return interpolate(properties, text.replace(STARTS_WITH + candidate + ENDS_WITH, properties.getProperty(candidate)));
            } else {
                return text.substring(0, start) + interpolate(properties, text.substring(start));
            }
        }
    }

}
