package io.sumac.propertyutils;

import io.sumac.propertyutils.annotations.NotNull;
import io.sumac.propertyutils.annotations.Property;
import io.sumac.propertyutils.utility.Executable;
import io.sumac.propertyutils.utility.IOThrowingSupplier;
import io.sumac.propertyutils.utility.PreCondition;
import io.sumac.propertyutils.annotations.Interpolates;
import static io.sumac.propertyutils.utility.TypeTransformer.*;

import java.io.IOException;
import java.lang.reflect.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extension of {@code java.util.Properties} that adds some additional
 * convenience methods such as additional property getters that can cast the result to primitive types like int or double,
 * methods that return an {@code Optional} object instead of a nullable value,
 * and methods that will automatically interpolate embedded placeholder keys within a property value.
 * <p>
 * For example, consider the following properties file:
 * <pre>
 *     firstName=Bill
 *     lastName=Dent
 *     fullName=${lastName}, ${fullName}
 * </pre>
 * <p>
 * The following code:
 *
 * <pre>
 *     Properties props = ...
 *     System.out.println(props.getString("fullName").get());
 * </pre>
 * <p>
 * ... will output "<code>Dent, Bill</code>" as the placeholders ${lastName} and ${firstName} are automatically interpolated.
 *
 * @author ross
 */
public class Properties extends java.util.Properties {

    private Function<String, String> decryptor = (s) -> s; // placeholder
    private Function<String, String> interpolator = (s) -> InterpolationHelper.interpolate(this, s);
    private Executable validator = () -> {
        throw new IllegalStateException("No registered validator. See setValidator(Executable validator) method.");
    }; // placeholder

    public static Properties from(Map<?, ?> map) {
        return new Properties(map);
    }

    public Properties() {
        super();
    }

    public Properties(Map<?, ?> properties) {
        super();
        loadFromMap(properties);
    }

    @Interpolates
    public Optional<String> getString(String key) {
        if (this.containsKey(key)) {
            return Optional.of(decryptor.apply(interpolate(this.getProperty(key))));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public String getStringRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return decryptor.apply(interpolate(this.getProperty(key)));
    }

    @Interpolates
    public Optional<Boolean> getBoolean(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toBoolean(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public boolean getBooleanRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toBoolean(decryptor.apply(interpolate(this.getProperty(key))));
    }

    @Interpolates
    public Optional<Integer> getInt(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toInt(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public int getIntRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toInt(decryptor.apply(interpolate(this.getProperty(key))));
    }

    @Interpolates
    public Optional<Long> getLong(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toLong(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public long getLongRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toLong(decryptor.apply(interpolate(this.getProperty(key))));
    }

    @Interpolates
    public Optional<Double> getDouble(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toDouble(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public double getDoubleRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toDouble(decryptor.apply(interpolate(this.getProperty(key))));
    }

    @Interpolates
    public Optional<Float> getFloat(String key) {
        if (this.containsKey(key)) {
            return Optional.of(toFloat(decryptor.apply(interpolate(this.getProperty(key)))));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public float getFloatRequired(String key) {
        rejectIfRequiredKeyNotFound(key);
        return toFloat(decryptor.apply(interpolate(this.getProperty(key))));
    }

    @Interpolates
    public Optional<Date> getDate(String key, String pattern) {
        if (this.containsKey(key)) {
            return Optional.of(toDate(decryptor.apply(interpolate(this.getProperty(key))), pattern));
        } else {
            return Optional.empty();
        }
    }

    @Interpolates
    public Date getDateRequired(String key, String pattern) {
        rejectIfRequiredKeyNotFound(key);
        return toDate(decryptor.apply(interpolate(this.getProperty(key))), pattern);
    }

    @Interpolates
    public void show() {
        TreeSet<String> sortedKeys = new TreeSet<>(this.stringPropertyNames());
        sortedKeys.forEach(k -> System.out.println(k + "=" + interpolate(this.getProperty(k))));
    }

    @Interpolates
    public void show(IOThrowingSupplier<String> customFormatter) {
        try {
            System.out.println(interpolate(PreCondition.Result.notNull(customFormatter.get())));
        } catch (IOException e) {
            throw PropertyResolverException.from(e);
        }
    }

    public void loadFromMap(@NotNull Map<?, ?> map) {
        PreCondition.Parameter.notNull(map);
        this.putAll(map);
    }

    public void loadFromSource(IOThrowingSupplier<java.util.Properties> source) throws IOException {
        loadFromSource(Collections.singletonList(source));
    }

    public void loadFromSource(List<IOThrowingSupplier<java.util.Properties>> sources) throws IOException {
        for (IOThrowingSupplier<java.util.Properties> source : sources) {
            loadFromMap(source.get());
        }
    }

    public void assertContainsKey(String... key) {
        assertContainsKeys(Arrays.asList(key));
    }

    public void assertContainsKeys(List<String> keys) {
        validate(() -> {
            List<String> missingKeys = new ArrayList<String>();
            keys.forEach(key -> {
                if (!containsKey(key)) {
                    missingKeys.add(key);
                }
            });
            if (!missingKeys.isEmpty()) {
                throw PropertyResolverException.propertiesNotFound(missingKeys);
            }
        });
    }

    /**
     * This method will execute a custom validator that should throw a {@code RuntimeException} if this properties object is not valid.
     * For example, this method could be used to validate the properties during a startup phase and immediately throw an exception
     * if a property key required for the program to function is missing.
     * <p/>
     * Example usage:
     * <pre>
     *     Properties props = ...
     *     props.validate(() -> {
     *        if (!props.containsKey("service.url") {
     *            throw new IllegalStateException("Missing property: 'service.url'");
     *        }
     *     });
     * </pre>
     *
     * @param validator functionInterface holding the validation logic.
     */
    public void validate(Executable validator) {
        try {
            validator.execute();
        } catch (PropertyResolverException e) {
            throw e;
        } catch (RuntimeException e) {
            throw PropertyResolverException.validationFailure(e);
        }
    }

    public void validate() {
        validate(validator);
    }

    public void setDecryptor(Function<String, String> decryptor) {
        this.decryptor = decryptor;
    }

    public void setInterpolator(Function<String, String> interpolator) {
        this.interpolator = interpolator;
    }

    /**
     * The {@code Executable} provided should validate the properties in the map and should throw some kind of RuntimeException,
     * probably IllegalStateException, if some requirement is not satisfied.
     *
     * @param validator functionalInterface containing validation logic
     */
    public void setValidator(Executable validator) {
        this.validator = validator;
    }

    public Properties filterByRegex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Properties result = new Properties();
        this.forEach((k, v) -> {
            Matcher matcher = pattern.matcher(k.toString());
            if (matcher.matches()) {
                result.put(k, v);
            }
        });
        return result;
    }

    public Properties filterByStartsWith(String keyStartsWith) {
        Properties result = new Properties();
        this.forEach((k, v) -> {
            if (k instanceof String && k.toString().startsWith(keyStartsWith + ".")) {
                result.put(k, v);
            }
        });
        return result;
    }

    public Properties getChildProperties(String parentPropertyKey) {
        Properties result = new Properties();
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

    @Interpolates
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

    public final <T> T to(Class<T> type) {
        try {
            validateConstructor(type);
            T clazz = construct(type);
            fillIn(clazz);
            return clazz;
        } catch (PropertyResolverException e) {
            throw e;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | SecurityException e) {
            throw PropertyResolverException.wrapCheckedReflectionExceptions(e);
        }
    }

    public final void fillIn(Object obj) {
        try {
            populateFields(obj);
            populateBySetters(obj);
        } catch (PropertyResolverException e) {
            throw e;
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw PropertyResolverException.wrapCheckedReflectionExceptions(e);
        }
    }

    private void validateConstructor(Class<?> type) {
        if (type.getDeclaredConstructors().length > 1) {
            throw PropertyResolverException.tooManyConstructors(type.getDeclaredConstructors().length);
        }
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            for (Parameter parameter : constructor.getParameters()) {
                if (!parameter.isAnnotationPresent(Property.class)) {
                    throw PropertyResolverException.constructorArgNotAnnotated(parameter);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T construct(Class<T> type)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Object> properties = new ArrayList<>();
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Parameter[] parameters = constructor.getParameters();
        for (Parameter parameter : parameters) {
            Property property = parameter.getAnnotation(Property.class);
            if (isString(parameter.getType())) {
                properties.add(handleOptional(property, getString(property.name())));
            } else if (isLong(parameter.getType())) {
                properties.add(handleOptional(property, getLong(property.name())));
            } else if (isInt(parameter.getType())) {
                properties.add(handleOptional(property, getInt(property.name())));
            } else if (isDouble(parameter.getType())) {
                properties.add(handleOptional(property, getDouble(property.name())));
            } else if (isFloat(parameter.getType())) {
                properties.add(handleOptional(property, getFloat(property.name())));
            } else if (isBoolean(parameter.getType())) {
                properties.add(handleOptional(property, getBoolean(property.name())));
            } else {
                throw PropertyResolverException.unsupportedType(parameter);
            }
        }
        return (T) constructor.newInstance(properties.toArray());
    }

    private <T> void populateFields(T obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Property.class)) {
                setPrimitives(field, obj);
            }
        }
    }

    private <T> void populateBySetters(T obj) throws IllegalAccessException, InvocationTargetException {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Property.class)) {
                validateSetterMethod(method);
                setPrimitives(method, obj);
            }
        }
    }

    private void setPrimitives(Field field, Object obj) throws IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        Property property = field.getAnnotation(Property.class);
        if (isString(field.getType())) {
            field.set(obj, handleOptional(property, getString(property.name())));
        } else if (isLong(field.getType())) {
            field.set(obj, handleOptional(property, getLong(property.name())));
        } else if (isInt(field.getType())) {
            field.set(obj, handleOptional(property, getInt(property.name())));
        } else if (isDouble(field.getType())) {
            field.set(obj, handleOptional(property, getDouble(property.name())));
        } else if (isFloat(field.getType())) {
            field.set(obj, handleOptional(property, getFloat(property.name())));
        } else if (isBoolean(field.getType())) {
            field.set(obj, handleOptional(property, getBoolean(property.name())));
        } else {
            throw PropertyResolverException.unsupportedType(field);
        }
        field.setAccessible(isAccessible);
    }

    private void validateSetterMethod(Method method) {
        final int count = method.getParameterCount();
        if (count < 1) {
            throw PropertyResolverException.noSetterArgs(method);
        } else if (count > 1) {
            throw PropertyResolverException.tooManySetterArgs(method);
        }
    }

    private void setPrimitives(Method method, Object obj) throws IllegalAccessException, InvocationTargetException {
        boolean isAccessible = method.isAccessible();
        method.setAccessible(true);
        Property property = method.getAnnotation(Property.class);
        Parameter parameter = method.getParameters()[0];
        if (isString(parameter.getType())) {
            method.invoke(obj, handleOptional(property, getString(property.name())));
        } else if (isLong(parameter.getType())) {
            method.invoke(obj, handleOptional(property, getLong(property.name())));
        } else if (isInt(parameter.getType())) {
            method.invoke(obj, handleOptional(property, getInt(property.name())));
        } else if (isDouble(parameter.getType())) {
            method.invoke(obj, handleOptional(property, getDouble(property.name())));
        } else if (isFloat(parameter.getType())) {
            method.invoke(obj, handleOptional(property, getFloat(property.name())));
        } else if (isBoolean(parameter.getType())) {
            method.invoke(obj, handleOptional(property, getBoolean(property.name())));
        } else {
            throw PropertyResolverException.unsupportedType(parameter);
        }
        method.setAccessible(isAccessible);
    }

    private Object handleOptional(Property property, Optional<?> value) {
        if (property.optional()) {
            if (value.isPresent()) {
                return value.get();
            } else {
                return null;
            }
        } else {
            if (value.isPresent()) {
                return value.get();
            } else {
                throw PropertyResolverException.propertyNotFound(property.name());
            }
        }
    }

    static class InterpolationHelper {

        private static final String STARTS_WITH = "${";
        private static final String ENDS_WITH = "}";
        private static final String DEFAULT_DELIMITER = ":";
        private static final String DEFAULT_VALUE = "";

        @Interpolates
        static String interpolate(java.util.Properties properties, String text) {

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
            String placeholder = text.substring(start, end);

            if (placeholder.contains(STARTS_WITH)) {
                if (!placeholder.contains(DEFAULT_DELIMITER) || placeholder.substring(0, placeholder.indexOf(DEFAULT_DELIMITER)).contains(STARTS_WITH)) {
                    throw PropertyResolverException.keyNestedInsideKey();
                } else {
                    throw PropertyResolverException.keyNestedInsideDefault();
                }
            }

            if (properties.containsKey(placeholder)) {
                return interpolate(properties, text.replace(STARTS_WITH + placeholder + ENDS_WITH, properties.getProperty(placeholder)));
            } else if (placeholder.contains(DEFAULT_DELIMITER)) {
                String key = placeholder.substring(0, placeholder.indexOf(DEFAULT_DELIMITER));

                // placeholder default is zero length string
                if (placeholder.indexOf(DEFAULT_DELIMITER) == placeholder.length() - 1) {
                    if (properties.containsKey(key)) {
                        return interpolate(properties, text.replace(STARTS_WITH + key + DEFAULT_DELIMITER + ENDS_WITH, properties.getProperty(key)));
                    } else {
                        return interpolate(properties, text.replace(STARTS_WITH + placeholder + ENDS_WITH, DEFAULT_VALUE));
                    }
                }
                String placeholderDefault = placeholder.substring(placeholder.indexOf(DEFAULT_DELIMITER) + 1);
                if (properties.containsKey(key)) {
                    return interpolate(properties, text.replace(STARTS_WITH + key + DEFAULT_DELIMITER + placeholderDefault + ENDS_WITH, properties.getProperty(key)));
                } else {
                    return interpolate(properties, text.replace(STARTS_WITH + placeholder + ENDS_WITH, placeholderDefault));
                }
            } else {
                return text.substring(0, start) + interpolate(properties, text.substring(start));
            }
        }
    }

}
