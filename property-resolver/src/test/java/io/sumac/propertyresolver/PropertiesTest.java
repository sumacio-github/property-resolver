package io.sumac.propertyresolver;

import io.sumac.propertyresolver.model.*;
import io.sumac.propertyresolver.utility.Executable;
import io.sumac.propertyresolver.utility.SimpleTextFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PropertiesTest {

    private static final Logger LOGGER = LogManager.getLogger(PropertiesTest.class);

    protected Properties systemUnderTest = new Properties();

    protected static final String STRING_KEY = "string";
    protected static final String INT_KEY = "int";
    protected static final String LONG_KEY = "long";
    protected static final String DOUBLE_KEY = "double";
    protected static final String FLOAT_KEY = "float";
    protected static final String BOOLEAN_KEY = "boolean";
    protected static final String DATE_KEY = "date";
    protected static final String EMPTY_KEY = "empty";
    protected static final String STRINGS_KEY_1 = "strings.1";
    protected static final String STRINGS_KEY_2 = "strings.2";
    protected static final String SOLO_STRING_KEY = "soloString.1";

    protected static final String STRING_KEY_NOT_FOUND = "string.notfound";
    protected static final String INT_KEY_NOT_FOUND = "int.notfound";
    protected static final String LONG_KEY_NOT_FOUND = "long.notfound";
    protected static final String DOUBLE_KEY_NOT_FOUND = "double.notfound";
    protected static final String FLOAT_KEY_NOT_FOUND = "float.notfound";
    protected static final String BOOLEAN_KEY_NOT_FOUND = "boolean.notfound";
    protected static final String DATE_KEY_NOT_FOUND = "date.notfound";
    protected static final String EMPTY_KEY_NOT_FOUND = "empty.notfound";
    protected static final String STRINGS_KEY_1_NOT_FOUND = "strings.1.notfound";
    protected static final String STRINGS_KEY_2_NOT_FOUND = "strings.2.notfound";
    protected static final String SOLO_STRING_KEY_NOT_FOUND = "soloString.1.notfound";

    protected static final String STRING_VALUE = "hello world";
    protected static final int INT_VALUE = 32;
    protected static final long LONG_VALUE = 64;
    protected static final double DOUBLE_VALUE = 2.2;
    protected static final float FLOAT_VALUE = 1.1f;
    protected static final boolean BOOLEAN_VALUE = true;
    protected static final Date DATE_VALUE;
    protected static final String EMPTY_VALUE = "";
    protected static final String STRINGS_VALUE_1 = "hello";
    protected static final String STRINGS_VALUE_2 = "goodbye";
    protected static final String SOLO_STRING_VALUE = "solo";

    protected static final String INT_STRING_VALUE = INT_VALUE + "";
    protected static final String LONG_STRING_VALUE = LONG_VALUE + "";
    protected static final String DOUBLE_STRING_VALUE = DOUBLE_VALUE + "";
    protected static final String FLOAT_STRING_VALUE = FLOAT_VALUE + "";
    protected static final String BOOLEAN_STRING_VALUE = BOOLEAN_VALUE + "";
    protected static final String DATE_STRING_VALUE = "1984-08-17T21:42:27.639-05:00";

    protected static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    static {
        try {
            DATE_VALUE = new SimpleDateFormat(DATE_PATTERN).parse(DATE_STRING_VALUE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("test.properties");
        systemUnderTest.load(in);
        in.close();
    }

    @Test
    public void testGetString_exists() {
        assertThat(systemUnderTest.getPropertyOptional(STRING_KEY).get(), is(STRING_VALUE));
    }

    @Test
    public void testGetString_notExists() {
        assertThat(systemUnderTest.getPropertyOptional(STRING_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetStringRequired_exists() {
        assertThat(systemUnderTest.getPropertyRequired(STRING_KEY), is(STRING_VALUE));
    }

    @Test
    public void testGetStringRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getPropertyRequired(STRING_KEY_NOT_FOUND));
    }

    @Test
    public void testGetBoolean_exists() {
        assertThat(systemUnderTest.getBooleanOptional(BOOLEAN_KEY).get(), is(BOOLEAN_VALUE));
    }

    @Test
    public void testGetBoolean_notExists() {
        assertThat(systemUnderTest.getBooleanOptional(BOOLEAN_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetBooleanRequired_exists() {
        assertThat(systemUnderTest.getBooleanRequired(BOOLEAN_KEY), is(BOOLEAN_VALUE));
    }

    @Test
    public void testGetBooleanRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getBooleanRequired(BOOLEAN_KEY_NOT_FOUND));
    }

    @Test
    public void testGetInt_exists() {
        assertThat(systemUnderTest.getIntOptional(INT_KEY).get(), is(INT_VALUE));
    }

    @Test
    public void testGetInt_notExists() {
        assertThat(systemUnderTest.getIntOptional(INT_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetIntRequired_exists() {
        assertThat(systemUnderTest.getIntRequired(INT_KEY), is(INT_VALUE));
    }

    @Test
    public void testGetIntRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getIntRequired(INT_KEY_NOT_FOUND));
    }

    @Test
    public void testGetLong_exists() {
        assertThat(systemUnderTest.getLongOptional(LONG_KEY).get(), is(LONG_VALUE));
    }

    @Test
    public void testGetLong_notExists() {
        assertThat(systemUnderTest.getLongOptional(LONG_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetLongRequired_exists() {
        assertThat(systemUnderTest.getLongRequired(LONG_KEY), is(LONG_VALUE));
    }

    @Test
    public void testGetLongRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getLongRequired(LONG_KEY_NOT_FOUND));
    }

    @Test
    public void testGetDouble_exists() {
        assertThat(systemUnderTest.getDoubleOptional(DOUBLE_KEY).get(), is(DOUBLE_VALUE));
    }

    @Test
    public void testGetDouble_notExists() {
        assertThat(systemUnderTest.getDoubleOptional(DOUBLE_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetDoubleRequired_exists() {
        assertThat(systemUnderTest.getDoubleRequired(DOUBLE_KEY), is(DOUBLE_VALUE));
    }

    @Test
    public void testGetDoubleRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getDoubleRequired(DOUBLE_KEY_NOT_FOUND));
    }

    @Test
    public void testGetFloat_exists() {
        assertThat(systemUnderTest.getFloatOptional(FLOAT_KEY).get(), is(FLOAT_VALUE));
    }

    @Test
    public void testGetFloat_notExists() {
        assertThat(systemUnderTest.getFloatOptional(FLOAT_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetFloatRequired_exists() {
        assertThat(systemUnderTest.getFloatRequired(FLOAT_KEY), is(FLOAT_VALUE));
    }

    @Test
    public void testGetFloatRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getFloatRequired(FLOAT_KEY_NOT_FOUND));
    }

    @Test
    public void testGetDate_exists() {
        assertThat(systemUnderTest.getDateOptional(DATE_KEY, DATE_PATTERN).get(), is(DATE_VALUE));
    }

    @Test
    public void testGetDate_notExists() {
        assertThat(systemUnderTest.getDateOptional(DATE_KEY_NOT_FOUND, DATE_PATTERN).isPresent(), is(false));
    }

    @Test
    public void testGetDateRequired_exists() {
        assertThat(systemUnderTest.getDateRequired(DATE_KEY, DATE_PATTERN), is(DATE_VALUE));
    }

    @Test
    public void testGetDateRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getDateRequired(DATE_KEY_NOT_FOUND, DATE_PATTERN));
    }

    @Test
    public void testGetEmpty_exists() {
        assertThat(systemUnderTest.getPropertyOptional(EMPTY_KEY).get(), is(EMPTY_VALUE));
    }

    @Test
    public void testGetEmpty_notExists() {
        assertThat(systemUnderTest.getPropertyOptional(EMPTY_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetEmptyRequired_exists() {
        assertThat(systemUnderTest.getPropertyRequired(EMPTY_KEY), is(EMPTY_VALUE));
    }

    @Test
    public void testGetEmptyRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getPropertyRequired(EMPTY_KEY_NOT_FOUND));
    }

    @Test
    public void testGetChildProperties_object() {
        testGetChildProperties("object.");
        testGetChildProperties("list.1.");
        testGetChildProperties("list.2.");
    }

    private void testGetChildProperties(String parent) {
        Properties props = systemUnderTest.getSubset(parent);
        assertThat(parent + STRING_KEY, props.getPropertyRequired(STRING_KEY), is(STRING_VALUE));
        assertThat(parent + INT_KEY, props.getPropertyRequired(INT_KEY), is(INT_STRING_VALUE));
        assertThat(parent + LONG_KEY, props.getPropertyRequired(LONG_KEY), is(LONG_STRING_VALUE));
        assertThat(parent + DOUBLE_KEY, props.getPropertyRequired(DOUBLE_KEY), is(DOUBLE_STRING_VALUE));
        assertThat(parent + FLOAT_KEY, props.getPropertyRequired(FLOAT_KEY), is(FLOAT_STRING_VALUE));
        assertThat(parent + BOOLEAN_KEY, props.getPropertyRequired(BOOLEAN_KEY), is(BOOLEAN_STRING_VALUE));
        assertThat(parent + DATE_KEY, props.getPropertyRequired(DATE_KEY), is(DATE_STRING_VALUE));
        assertThat(parent + EMPTY_KEY, props.getPropertyRequired(EMPTY_KEY), is(EMPTY_VALUE));
        assertThat(parent + STRINGS_KEY_1, props.getPropertyRequired(STRINGS_KEY_1), is(STRINGS_VALUE_1));
        assertThat(parent + STRINGS_KEY_2, props.getPropertyRequired(STRINGS_KEY_2), is(STRINGS_VALUE_2));
        assertThat(parent + SOLO_STRING_KEY, props.getPropertyRequired(SOLO_STRING_KEY), is(SOLO_STRING_VALUE));
    }

    @Test
    public void testFilterByStartsWith_object() {
        testFilterByStartsWith("object");
        testFilterByStartsWith("list.1");
        testFilterByStartsWith("list.2");
    }

    private void testFilterByStartsWith(String parent) {
        Properties props = systemUnderTest.filterByStartsWith(parent);
        assertThat(parent + STRING_KEY, props.getPropertyRequired(parent + "." + STRING_KEY), is(STRING_VALUE));
        assertThat(parent + INT_KEY, props.getPropertyRequired(parent + "." + INT_KEY), is(INT_STRING_VALUE));
        assertThat(parent + LONG_KEY, props.getPropertyRequired(parent + "." + LONG_KEY), is(LONG_STRING_VALUE));
        assertThat(parent + DOUBLE_KEY, props.getPropertyRequired(parent + "." + DOUBLE_KEY), is(DOUBLE_STRING_VALUE));
        assertThat(parent + FLOAT_KEY, props.getPropertyRequired(parent + "." + FLOAT_KEY), is(FLOAT_STRING_VALUE));
        assertThat(parent + BOOLEAN_KEY, props.getPropertyRequired(parent + "." + BOOLEAN_KEY), is(BOOLEAN_STRING_VALUE));
        assertThat(parent + DATE_KEY, props.getPropertyRequired(parent + "." + DATE_KEY), is(DATE_STRING_VALUE));
        assertThat(parent + EMPTY_KEY, props.getPropertyRequired(parent + "." + EMPTY_KEY), is(EMPTY_VALUE));
        assertThat(parent + STRINGS_KEY_1, props.getPropertyRequired(parent + "." + STRINGS_KEY_1), is(STRINGS_VALUE_1));
        assertThat(parent + STRINGS_KEY_2, props.getPropertyRequired(parent + "." + STRINGS_KEY_2), is(STRINGS_VALUE_2));
        assertThat(parent + SOLO_STRING_KEY, props.getPropertyRequired(parent + "." + SOLO_STRING_KEY), is(SOLO_STRING_VALUE));
    }

    @Test
    public void testFilterByRegex_object() {
        testFilterByRegex("object", "^object.*");
        testFilterByRegex("list.1", "^list\\.1.*");
        testFilterByRegex("list.2", "^list\\.2.*");
    }

    private void testFilterByRegex(String parent, String regex) {
        Properties props = systemUnderTest.filterByRegex(regex);
        assertThat(parent + STRING_KEY, props.getPropertyRequired(parent + "." + STRING_KEY), is(STRING_VALUE));
        assertThat(parent + INT_KEY, props.getPropertyRequired(parent + "." + INT_KEY), is(INT_STRING_VALUE));
        assertThat(parent + LONG_KEY, props.getPropertyRequired(parent + "." + LONG_KEY), is(LONG_STRING_VALUE));
        assertThat(parent + DOUBLE_KEY, props.getPropertyRequired(parent + "." + DOUBLE_KEY), is(DOUBLE_STRING_VALUE));
        assertThat(parent + FLOAT_KEY, props.getPropertyRequired(parent + "." + FLOAT_KEY), is(FLOAT_STRING_VALUE));
        assertThat(parent + BOOLEAN_KEY, props.getPropertyRequired(parent + "." + BOOLEAN_KEY), is(BOOLEAN_STRING_VALUE));
        assertThat(parent + DATE_KEY, props.getPropertyRequired(parent + "." + DATE_KEY), is(DATE_STRING_VALUE));
        assertThat(parent + EMPTY_KEY, props.getPropertyRequired(parent + "." + EMPTY_KEY), is(EMPTY_VALUE));
        assertThat(parent + STRINGS_KEY_1, props.getPropertyRequired(parent + "." + STRINGS_KEY_1), is(STRINGS_VALUE_1));
        assertThat(parent + STRINGS_KEY_2, props.getPropertyRequired(parent + "." + STRINGS_KEY_2), is(STRINGS_VALUE_2));
        assertThat(parent + SOLO_STRING_KEY, props.getPropertyRequired(parent + "." + SOLO_STRING_KEY), is(SOLO_STRING_VALUE));
    }

    @Test
    public void testInterpolate() throws IOException {
        String text = SimpleTextFileReader.readFromClasspath("interpolate_1.txt");
        String expected = SimpleTextFileReader.readFromClasspath("interpolate_2.txt");
        assertThat(systemUnderTest.interpolate(text), is(expected));
    }

    @Test
    public void testInterpolate_withDefaults() throws IOException {
        String text = SimpleTextFileReader.readFromClasspath("interpolate_with_default_1.txt");
        String expected = SimpleTextFileReader.readFromClasspath("interpolate_with_default_2.txt");
        assertThat(systemUnderTest.interpolate(text), is(expected));
    }

    @Test
    public void testInterpolate_withDefaultsNotFound() throws IOException {
        String text = SimpleTextFileReader.readFromClasspath("interpolate_not_found_with_default_1.txt");
        String expected = SimpleTextFileReader.readFromClasspath("interpolate_not_found_with_default_2.txt");
        assertThat(systemUnderTest.interpolate(text), is(expected));
    }

    @Test
    public void testValidate() {
        Exception e = Assertions.assertThrows(PropertyResolverException.ValidationException.class, () -> systemUnderTest.validate());
        assertThat(e.getCause(), isA(IllegalStateException.class));
        assertThat(e.getCause().getMessage(), is("No registered validator. See setValidator(Executable validator) method."));
        Executable validator = () -> {
            if (!systemUnderTest.containsKey("something")) {
                throw new IllegalArgumentException("invalid");
            }
        };
        e = Assertions.assertThrows(PropertyResolverException.ValidationException.class, () -> systemUnderTest.validate(validator));
        assertThat(e.getCause(), isA(IllegalArgumentException.class));
        assertThat(e.getCause().getMessage(), is("invalid"));
        systemUnderTest.setValidator(validator);
        e = Assertions.assertThrows(PropertyResolverException.ValidationException.class, () -> systemUnderTest.validate());
        assertThat(e.getCause(), isA(IllegalArgumentException.class));
        assertThat(e.getCause().getMessage(), is("invalid"));
    }

    @Test
    public void testAssertContainsKey() {
        systemUnderTest.assertContainsKey("string");
        Exception e = Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class, () -> systemUnderTest.assertContainsKey("string", "fake"));
        assertThat(e.getMessage(), is("Property not found: '[fake]'"));
    }

    @Test
    public void testInterpolateKeyNested() {
        Exception e = Assertions.assertThrows(PropertyResolverException.InterpolationErrorException.class, () -> systemUnderTest.interpolate("1${${test}}2"));
        assertThat(e.getMessage(), is("Interpolation error: Cannot nest a placeholder key inside of a property key."));
        e = Assertions.assertThrows(PropertyResolverException.InterpolationErrorException.class, () -> systemUnderTest.interpolate("1${${test}:}2"));
        assertThat(e.getMessage(), is("Interpolation error: Cannot nest a placeholder key inside of a property key."));
        e = Assertions.assertThrows(PropertyResolverException.InterpolationErrorException.class, () -> systemUnderTest.interpolate("1${:${test}}2"));
        assertThat(e.getMessage(), is("Interpolation error: Cannot nest a placeholder key inside of a default value."));
    }

    @Test
    public void toTest_fields() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model1 output = properties.to(Model1.class);
        validate(output);
    }

    @Test
    public void toTest_methods() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model2 output = properties.to(Model2.class);
        validate(output);
    }

    @Test
    public void toTest_parameters() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model3 output = properties.to(Model3.class);
        validate(output);
    }

    @Test
    public void fillInTest_fields() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model1 model = new Model1();
        properties.fillIn(model);
        validate(model);
    }

    @Test
    public void fillInTest_methods() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model2 model = new Model2();
        properties.fillIn(model);
        validate(model);
    }

    @Test
    public void toTest_missingFields() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model4.class));
        assertThat(output.getMessage(), is("Property not found: '[test.not_found.string]'"));
    }

    @Test
    public void toTest_missingMethods() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model5.class));
        assertThat(output.getMessage(), is("Property not found: '[test.not_found.string]'"));
    }

    @Test
    public void toTest_missingParameters() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model6.class));
        assertThat(output.getMessage(), is("Property not found: '[test.not_found.string]'"));
    }

    @Test
    public void toTest_optionalFields() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model7 output = properties.to(Model7.class);
        assertAll(() -> assertThat(output.getFoundString(), is("hello world")),
                () -> assertThat(output.getNotFoundString(), nullValue()));
    }

    @Test
    public void toTest_optionalMethods() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model8 output = properties.to(Model8.class);
        assertAll(() -> assertThat(output.getFoundString(), is("hello world")),
                () -> assertThat(output.getNotFoundString(), nullValue()));
    }

    @Test
    public void toTest_optionalParameters() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        Model9 output = properties.to(Model9.class);
        assertAll(() -> assertThat(output.getFoundString(), is("hello world")),
                () -> assertThat(output.getNotFoundString(), nullValue()));
    }

    @Test
    public void toTestInvalidTypeField() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model10.class));
        assertThat(output.getMessage(), is("Field type not supported: class java.util.Date"));
    }

    @Test
    public void toTestInvalidTypeMethod() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model11.class));
        assertThat(output.getMessage(), is("Parameter type not supported: class java.util.Date"));
    }

    @Test
    public void toTestInvalidTypeParameters() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model12.class));
        assertThat(output.getMessage(), is("Parameter type not supported: class java.util.Date"));
    }

    @Test
    public void toTestTooManyConstructors() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model13.class));
        assertThat(output.getMessage(), is("Too many constructors: 2"));
    }

    @Test
    public void toTestParameterArgNotAnnotated() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model14.class));
        assertThat(output.getMessage(), is("Parameter not annotated: arg1"));
    }

    @Test
    public void toTestNoSetterArgs() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model15.class));
        assertThat(output.getMessage(), is("No arguments: setStr"));
    }

    @Test
    public void toTestTooManySetterArgs() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("inject_test.properties");
        properties.load(in);
        PropertyResolverException output = Assertions.assertThrows(PropertyResolverException.class,
                () -> properties.to(Model16.class));
        assertThat(output.getMessage(), is("Too many arguments: setStr: 2"));
    }

    @Test
    public void testFromProperties() {
        Properties props = new Properties();
        props.put("test.found.string", "hello world");
        Properties properties = Properties.from(props);
        Model17 output = properties.to(Model17.class);
        assertThat(output.getStr(), is("hello world"));
    }

    private void validate(Model1 model) {
        assertAll(() -> assertThat(model.getFoundString(), is("hello world")),
                () -> assertThat(model.getFoundInteger(), is(32)),
                () -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
                () -> assertThat(model.getFoundLong(), is(64L)),
                () -> assertThat(model.getFoundLongPrimitive(), is(64L)),
                () -> assertThat(model.getFoundDouble(), is(2.2)),
                () -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
                () -> assertThat(model.getFoundFloat(), is(1.1F)),
                () -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
                () -> assertThat(model.getFoundBoolean(), is(true)),
                () -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
    }

    private void validate(Model2 model) {
        assertAll(() -> assertThat(model.getFoundString(), is("hello world")),
                () -> assertThat(model.getFoundInteger(), is(32)),
                () -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
                () -> assertThat(model.getFoundLong(), is(64L)),
                () -> assertThat(model.getFoundLongPrimitive(), is(64L)),
                () -> assertThat(model.getFoundDouble(), is(2.2)),
                () -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
                () -> assertThat(model.getFoundFloat(), is(1.1F)),
                () -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
                () -> assertThat(model.getFoundBoolean(), is(true)),
                () -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
    }

    private void validate(Model3 model) {
        assertAll(() -> assertThat(model.getFoundString(), is("hello world")),
                () -> assertThat(model.getFoundInteger(), is(32)),
                () -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
                () -> assertThat(model.getFoundLong(), is(64L)),
                () -> assertThat(model.getFoundLongPrimitive(), is(64L)),
                () -> assertThat(model.getFoundDouble(), is(2.2)),
                () -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
                () -> assertThat(model.getFoundFloat(), is(1.1F)),
                () -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
                () -> assertThat(model.getFoundBoolean(), is(true)),
                () -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
    }

}
