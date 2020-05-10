package io.sumac.propertyresolver;

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

    protected EnrichedProperties systemUnderTest;

    protected static final String STRING_KEY = "test.string";
    protected static final String INT_KEY = "test.int";
    protected static final String LONG_KEY = "test.long";
    protected static final String DOUBLE_KEY = "test.double";
    protected static final String FLOAT_KEY = "test.float";
    protected static final String BOOLEAN_KEY = "test.boolean";
    protected static final String DATE_KEY = "test.date";

    protected static final String STRING_KEY_NOT_FOUND = "test.string.notfound";
    protected static final String INT_KEY_NOT_FOUND = "test.int.notfound";
    protected static final String LONG_KEY_NOT_FOUND = "test.long.notfound";
    protected static final String DOUBLE_KEY_NOT_FOUND = "test.double.notfound";
    protected static final String FLOAT_KEY_NOT_FOUND = "test.float.notfound";
    protected static final String BOOLEAN_KEY_NOT_FOUND = "test.boolean.notfound";
    protected static final String DATE_KEY_NOT_FOUND = "test.date.notfound";

    protected static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    protected static final String STRING_VALUE = "hello world";
    protected static final int INT_VALUE = 32;
    protected static final long LONG_VALUE = 64;
    protected static final double DOUBLE_VALUE = 2.2;
    protected static final float FLOAT_VALUE = 1.1f;
    protected static final boolean BOOLEAN_VALUE = true;
    protected static final Date DATE_VALUE;

    static {
        try {
            DATE_VALUE = new SimpleDateFormat(DATE_PATTERN).parse("1984-08-17T21:42:27.639-05:00");
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

}