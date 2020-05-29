package io.sumac.propertyhelper;

import io.sumac.propertyhelper.utility.SimpleTextFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class AbstractEnrichedPropertyResolverTest {

    protected Properties systemUnderTest;

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

    protected String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Test
    public void testGetString_exists() {
        assertThat(systemUnderTest.getString(STRING_KEY).get(), is(STRING_VALUE));
    }

    @Test
    public void testGetString_notExists() {
        assertThat(systemUnderTest.getString(STRING_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetStringRequired_exists() {
        assertThat(systemUnderTest.getStringRequired(STRING_KEY), is(STRING_VALUE));
    }

    @Test
    public void testGetStringRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getStringRequired(STRING_KEY_NOT_FOUND));
    }

    @Test
    public void testGetBoolean_exists() {
        assertThat(systemUnderTest.getBoolean(BOOLEAN_KEY).get(), is(BOOLEAN_VALUE));
    }

    @Test
    public void testGetBoolean_notExists() {
        assertThat(systemUnderTest.getBoolean(BOOLEAN_KEY_NOT_FOUND).isPresent(), is(false));
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
        assertThat(systemUnderTest.getInt(INT_KEY).get(), is(INT_VALUE));
    }

    @Test
    public void testGetInt_notExists() {
        assertThat(systemUnderTest.getInt(INT_KEY_NOT_FOUND).isPresent(), is(false));
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
        assertThat(systemUnderTest.getLong(LONG_KEY).get(), is(LONG_VALUE));
    }

    @Test
    public void testGetLong_notExists() {
        assertThat(systemUnderTest.getLong(LONG_KEY_NOT_FOUND).isPresent(), is(false));
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
        assertThat(systemUnderTest.getDouble(DOUBLE_KEY).get(), is(DOUBLE_VALUE));
    }

    @Test
    public void testGetDouble_notExists() {
        assertThat(systemUnderTest.getDouble(DOUBLE_KEY_NOT_FOUND).isPresent(), is(false));
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
        assertThat(systemUnderTest.getFloat(FLOAT_KEY).get(), is(FLOAT_VALUE));
    }

    @Test
    public void testGetFloat_notExists() {
        assertThat(systemUnderTest.getFloat(FLOAT_KEY_NOT_FOUND).isPresent(), is(false));
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
        assertThat(systemUnderTest.getDate(DATE_KEY, DATE_PATTERN).get(), is(DATE_VALUE));
    }

    @Test
    public void testGetDate_notExists() {
        assertThat(systemUnderTest.getDate(DATE_KEY_NOT_FOUND, DATE_PATTERN).isPresent(), is(false));
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
        assertThat(systemUnderTest.getString(EMPTY_KEY).get(), is(EMPTY_VALUE));
    }

    @Test
    public void testGetEmpty_notExists() {
        assertThat(systemUnderTest.getString(EMPTY_KEY_NOT_FOUND).isPresent(), is(false));
    }

    @Test
    public void testGetEmptyRequired_exists() {
        assertThat(systemUnderTest.getStringRequired(EMPTY_KEY), is(EMPTY_VALUE));
    }

    @Test
    public void testGetEmptyRequired_notExists() {
        Assertions.assertThrows(PropertyResolverException.PropertyNotFoundException.class,
                () -> systemUnderTest.getStringRequired(EMPTY_KEY_NOT_FOUND));
    }

    @Test
    public void testGetChildProperties_object() {
        testGetChildProperties("object");
        testGetChildProperties("list.1");
        testGetChildProperties("list.2");
    }

    private void testGetChildProperties(String parent) {
        Properties props = systemUnderTest.getChildProperties(parent);
        assertThat(parent + STRING_KEY, props.getStringRequired(STRING_KEY), is(STRING_VALUE));
        assertThat(parent + INT_KEY, props.getStringRequired(INT_KEY), is(INT_STRING_VALUE));
        assertThat(parent + LONG_KEY, props.getStringRequired(LONG_KEY), is(LONG_STRING_VALUE));
        assertThat(parent + DOUBLE_KEY, props.getStringRequired(DOUBLE_KEY), is(DOUBLE_STRING_VALUE));
        assertThat(parent + FLOAT_KEY, props.getStringRequired(FLOAT_KEY), is(FLOAT_STRING_VALUE));
        assertThat(parent + BOOLEAN_KEY, props.getStringRequired(BOOLEAN_KEY), is(BOOLEAN_STRING_VALUE));
        assertThat(parent + DATE_KEY, props.getStringRequired(DATE_KEY), is(DATE_STRING_VALUE));
        assertThat(parent + EMPTY_KEY, props.getStringRequired(EMPTY_KEY), is(EMPTY_VALUE));
        assertThat(parent + STRINGS_KEY_1, props.getStringRequired(STRINGS_KEY_1), is(STRINGS_VALUE_1));
        assertThat(parent + STRINGS_KEY_2, props.getStringRequired(STRINGS_KEY_2), is(STRINGS_VALUE_2));
        assertThat(parent + SOLO_STRING_KEY, props.getStringRequired(SOLO_STRING_KEY), is(SOLO_STRING_VALUE));
    }

    @Test
    public void testFilterByStartsWith_object() {
        testFilterByStartsWith("object");
        testFilterByStartsWith("list.1");
        testFilterByStartsWith("list.2");
    }

    private void testFilterByStartsWith(String parent) {
        Properties props = systemUnderTest.filterByStartsWith(parent);
        assertThat(parent + STRING_KEY, props.getStringRequired(parent + "." + STRING_KEY), is(STRING_VALUE));
        assertThat(parent + INT_KEY, props.getStringRequired(parent + "." + INT_KEY), is(INT_STRING_VALUE));
        assertThat(parent + LONG_KEY, props.getStringRequired(parent + "." + LONG_KEY), is(LONG_STRING_VALUE));
        assertThat(parent + DOUBLE_KEY, props.getStringRequired(parent + "." + DOUBLE_KEY), is(DOUBLE_STRING_VALUE));
        assertThat(parent + FLOAT_KEY, props.getStringRequired(parent + "." + FLOAT_KEY), is(FLOAT_STRING_VALUE));
        assertThat(parent + BOOLEAN_KEY, props.getStringRequired(parent + "." + BOOLEAN_KEY), is(BOOLEAN_STRING_VALUE));
        assertThat(parent + DATE_KEY, props.getStringRequired(parent + "." + DATE_KEY), is(DATE_STRING_VALUE));
        assertThat(parent + EMPTY_KEY, props.getStringRequired(parent + "." + EMPTY_KEY), is(EMPTY_VALUE));
        assertThat(parent + STRINGS_KEY_1, props.getStringRequired(parent + "." + STRINGS_KEY_1), is(STRINGS_VALUE_1));
        assertThat(parent + STRINGS_KEY_2, props.getStringRequired(parent + "." + STRINGS_KEY_2), is(STRINGS_VALUE_2));
        assertThat(parent + SOLO_STRING_KEY, props.getStringRequired(parent + "." + SOLO_STRING_KEY), is(SOLO_STRING_VALUE));
    }

    @Test
    public void testFilterByRegex_object() {
        testFilterByRegex("object", "^object.*");
        testFilterByRegex("list.1", "^list\\.1.*");
        testFilterByRegex("list.2", "^list\\.2.*");
    }

    private void testFilterByRegex(String parent, String regex) {
        Properties props = systemUnderTest.filterByRegex(regex);
        assertThat(parent + STRING_KEY, props.getStringRequired(parent + "." + STRING_KEY), is(STRING_VALUE));
        assertThat(parent + INT_KEY, props.getStringRequired(parent + "." + INT_KEY), is(INT_STRING_VALUE));
        assertThat(parent + LONG_KEY, props.getStringRequired(parent + "." + LONG_KEY), is(LONG_STRING_VALUE));
        assertThat(parent + DOUBLE_KEY, props.getStringRequired(parent + "." + DOUBLE_KEY), is(DOUBLE_STRING_VALUE));
        assertThat(parent + FLOAT_KEY, props.getStringRequired(parent + "." + FLOAT_KEY), is(FLOAT_STRING_VALUE));
        assertThat(parent + BOOLEAN_KEY, props.getStringRequired(parent + "." + BOOLEAN_KEY), is(BOOLEAN_STRING_VALUE));
        assertThat(parent + DATE_KEY, props.getStringRequired(parent + "." + DATE_KEY), is(DATE_STRING_VALUE));
        assertThat(parent + EMPTY_KEY, props.getStringRequired(parent + "." + EMPTY_KEY), is(EMPTY_VALUE));
        assertThat(parent + STRINGS_KEY_1, props.getStringRequired(parent + "." + STRINGS_KEY_1), is(STRINGS_VALUE_1));
        assertThat(parent + STRINGS_KEY_2, props.getStringRequired(parent + "." + STRINGS_KEY_2), is(STRINGS_VALUE_2));
        assertThat(parent + SOLO_STRING_KEY, props.getStringRequired(parent + "." + SOLO_STRING_KEY), is(SOLO_STRING_VALUE));
    }

    @Test
    public void testInterpolate() throws IOException {
        String text = SimpleTextFileReader.readFromClasspath("interpolate_1.txt");
        String expected = SimpleTextFileReader.readFromClasspath("interpolate_2.txt");
        assertThat(systemUnderTest.interpolate(text), is(expected));
    }

//    @Test
//    public void testInterpolate_withDefaults() throws IOException {
//        String text = SimpleTextFileReader.readFromClasspath("interpolate_with_default_1.txt");
//        String expected = SimpleTextFileReader.readFromClasspath("interpolate_with_default_2.txt");
//        assertThat(systemUnderTest.interpolate(text), is(expected));
//    }
//
//    @Test
//    public void testInterpolate_withDefaultsNotFound() throws IOException {
//        String text = SimpleTextFileReader.readFromClasspath("interpolate_not_found_with_default_1.txt");
//        String expected = SimpleTextFileReader.readFromClasspath("interpolate_not_found_with_default_2.txt");
//        assertThat(systemUnderTest.interpolate(text), is(expected));
//    }

}
