package io.sumac.propertyresolver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.sumac.propertyresolver.PropertyResolverException.PropertyNotFoundException;

public class EnrichedPropertiesTest {

	private EnrichedProperties systemUnderTest;
	private EnrichedProperties systemUnderTestEmpty;
	private EnrichedProperties systemUnderTestAltEnvKeys;

	private static final String STRING_KEY = "test.string";
	private static final String INT_KEY = "test.int";
	private static final String LONG_KEY = "test.long";
	private static final String DOUBLE_KEY = "test.double";
	private static final String FLOAT_KEY = "test.float";
	private static final String BOOLEAN_KEY = "test.boolean";
	private static final String DATE_KEY = "test.date";
	private static final String APP_NAME_KEY = "environment.appName";
	private static final String APP_VERSION_KEY = "environment.appVersion";
	private static final String ACTIVE_PROFILE_KEY = "environment.activeProfile";
	private static final String APP_NAME_ALT_KEY = "app.name";
	private static final String APP_VERSION_ALT_KEY = "app.version";
	private static final String ACTIVE_PROFILE_ALT_KEY = "app.activeProfile";

	private static final String STRING_KEY_NOT_FOUND = "test.string.notfound";
	private static final String INT_KEY_NOT_FOUND = "test.int.notfound";
	private static final String LONG_KEY_NOT_FOUND = "test.long.notfound";
	private static final String DOUBLE_KEY_NOT_FOUND = "test.double.notfound";
	private static final String FLOAT_KEY_NOT_FOUND = "test.float.notfound";
	private static final String BOOLEAN_KEY_NOT_FOUND = "test.boolean.notfound";
	private static final String DATE_KEY_NOT_FOUND = "test.date.notfound";

	private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	private static final String STRING_VALUE = "testing";
	private static final int INT_VALUE = 4;
	private static final long LONG_VALUE = 3l;
	private static final double DOUBLE_VALUE = 2.2;
	private static final float FLOAT_VALUE = 1.1f;
	private static final boolean BOOLEAN_VALUE = true;
	private static final Date DATE_VALUE;
	static {
		try {
			DATE_VALUE = new SimpleDateFormat(DATE_PATTERN).parse("1984-08-17T21:42:27.639-05:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	private static final String APP_NAME_VALUE = "test_app";
	private static final String APP_VERSION_VALUE = "test_version";
	private static final String ACTIVE_PROFILE_VALUE = "test_profile";

	@BeforeEach
	public void setUp() throws JsonProcessingException, IOException {
		systemUnderTestEmpty = new EnrichedProperties();
		systemUnderTestAltEnvKeys = new EnrichedProperties();
		systemUnderTest = new EnrichedProperties();
		systemUnderTest.put(STRING_KEY, STRING_VALUE);
		systemUnderTest.put(INT_KEY, Integer.valueOf(INT_VALUE).toString());
		systemUnderTest.put(LONG_KEY, Long.valueOf(LONG_VALUE).toString());
		systemUnderTest.put(DOUBLE_KEY, Double.valueOf(DOUBLE_VALUE).toString());
		systemUnderTest.put(FLOAT_KEY, Float.valueOf(FLOAT_VALUE).toString());
		systemUnderTest.put(BOOLEAN_KEY, Boolean.TRUE.toString());
		systemUnderTest.put(DATE_KEY, new SimpleDateFormat(DATE_PATTERN).format(DATE_VALUE));
		systemUnderTestAltEnvKeys.putAll(systemUnderTest);
		systemUnderTest.put(APP_NAME_KEY, APP_NAME_VALUE);
		systemUnderTest.put(APP_VERSION_KEY, APP_VERSION_VALUE);
		systemUnderTest.put(ACTIVE_PROFILE_KEY, ACTIVE_PROFILE_VALUE);
		systemUnderTestAltEnvKeys.put(APP_NAME_ALT_KEY, APP_NAME_VALUE);
		systemUnderTestAltEnvKeys.getEnvironment().setAppNameKey(APP_NAME_ALT_KEY);
		systemUnderTestAltEnvKeys.put(APP_VERSION_ALT_KEY, APP_VERSION_VALUE);
		systemUnderTestAltEnvKeys.getEnvironment().setAppVersionKey(APP_VERSION_ALT_KEY);
		systemUnderTestAltEnvKeys.put(ACTIVE_PROFILE_ALT_KEY, ACTIVE_PROFILE_VALUE);
		systemUnderTestAltEnvKeys.getEnvironment().setActiveProfileKey(ACTIVE_PROFILE_ALT_KEY);
		System.out.println(systemUnderTest.toYamlString());
	}

	@Test
	public void testGetString_exists() {
		assertThat(systemUnderTest.getString(STRING_KEY), is(STRING_VALUE));
	}

	@Test
	public void testGetString_notExists() {
		assertThat(systemUnderTest.getString(STRING_KEY_NOT_FOUND), nullValue());
	}

	@Test
	public void testGetStringOptional_exists() {
		assertThat(systemUnderTest.getStringOptional(STRING_KEY).get(), is(STRING_VALUE));
	}

	@Test
	public void testGetStringOptional_notExists() {
		assertThat(systemUnderTest.getStringOptional(STRING_KEY_NOT_FOUND).isEmpty(), is(true));
	}

	@Test
	public void testGetStringRequired_exists() {
		assertThat(systemUnderTest.getStringRequired(STRING_KEY), is(STRING_VALUE));
	}

	@Test
	public void testGetStringRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getStringRequired(STRING_KEY_NOT_FOUND));
	}

	@Test
	public void testGetBoolean_exists() {
		assertThat(systemUnderTest.getBoolean(BOOLEAN_KEY), is(BOOLEAN_VALUE));
	}

	@Test
	public void testGetBoolean_notExists() {
		assertThat(systemUnderTest.getBoolean(BOOLEAN_KEY_NOT_FOUND), nullValue());
	}

	@Test
	public void testGetBooleanOptional_exists() {
		assertThat(systemUnderTest.getBooleanOptional(BOOLEAN_KEY).get(), is(BOOLEAN_VALUE));
	}

	@Test
	public void testGetBooleanOptional_notExists() {
		assertThat(systemUnderTest.getBooleanOptional(BOOLEAN_KEY_NOT_FOUND).isEmpty(), is(true));
	}

	@Test
	public void testGetBooleanRequired_exists() {
		assertThat(systemUnderTest.getBooleanRequired(BOOLEAN_KEY), is(BOOLEAN_VALUE));
	}

	@Test
	public void testGetBooleanRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getBooleanRequired(BOOLEAN_KEY_NOT_FOUND));
	}

	@Test
	public void testGetInt_exists() {
		assertThat(systemUnderTest.getInt(INT_KEY), is(INT_VALUE));
	}

	@Test
	public void testGetInt_notExists() {
		assertThat(systemUnderTest.getInt(INT_KEY_NOT_FOUND), nullValue());
	}

	@Test
	public void testGetIntOptional_exists() {
		assertThat(systemUnderTest.getIntOptional(INT_KEY).get(), is(INT_VALUE));
	}

	@Test
	public void testGetIntOptional_notExists() {
		assertThat(systemUnderTest.getIntOptional(INT_KEY_NOT_FOUND).isEmpty(), is(true));
	}

	@Test
	public void testGetIntRequired_exists() {
		assertThat(systemUnderTest.getIntRequired(INT_KEY), is(INT_VALUE));
	}

	@Test
	public void testGetIntRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getIntRequired(INT_KEY_NOT_FOUND));
	}

	@Test
	public void testGetLong_exists() {
		assertThat(systemUnderTest.getLong(LONG_KEY), is(LONG_VALUE));
	}

	@Test
	public void testGetLong_notExists() {
		assertThat(systemUnderTest.getLong(LONG_KEY_NOT_FOUND), nullValue());
	}

	@Test
	public void testGetLongOptional_exists() {
		assertThat(systemUnderTest.getLongOptional(LONG_KEY).get(), is(LONG_VALUE));
	}

	@Test
	public void testGetLongOptional_notExists() {
		assertThat(systemUnderTest.getLongOptional(LONG_KEY_NOT_FOUND).isEmpty(), is(true));
	}

	@Test
	public void testGetLongRequired_exists() {
		assertThat(systemUnderTest.getLongRequired(LONG_KEY), is(LONG_VALUE));
	}

	@Test
	public void testGetLongRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getLongRequired(LONG_KEY_NOT_FOUND));
	}

	@Test
	public void testGetDouble_exists() {
		assertThat(systemUnderTest.getDouble(DOUBLE_KEY), is(DOUBLE_VALUE));
	}

	@Test
	public void testGetDouble_notExists() {
		assertThat(systemUnderTest.getDouble(DOUBLE_KEY_NOT_FOUND), nullValue());
	}

	@Test
	public void testGetDoubleOptional_exists() {
		assertThat(systemUnderTest.getDoubleOptional(DOUBLE_KEY).get(), is(DOUBLE_VALUE));
	}

	@Test
	public void testGetDoubleOptional_notExists() {
		assertThat(systemUnderTest.getDoubleOptional(DOUBLE_KEY_NOT_FOUND).isEmpty(), is(true));
	}

	@Test
	public void testGetDoubleRequired_exists() {
		assertThat(systemUnderTest.getDoubleRequired(DOUBLE_KEY), is(DOUBLE_VALUE));
	}

	@Test
	public void testGetDoubleRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getDoubleRequired(DOUBLE_KEY_NOT_FOUND));
	}

	@Test
	public void testGetFloat_exists() {
		assertThat(systemUnderTest.getFloat(FLOAT_KEY), is(FLOAT_VALUE));
	}

	@Test
	public void testGetFloat_notExists() {
		assertThat(systemUnderTest.getFloat(FLOAT_KEY_NOT_FOUND), nullValue());
	}

	@Test
	public void testGetFloatOptional_exists() {
		assertThat(systemUnderTest.getFloatOptional(FLOAT_KEY).get(), is(FLOAT_VALUE));
	}

	@Test
	public void testGetFloatOptional_notExists() {
		assertThat(systemUnderTest.getFloatOptional(FLOAT_KEY_NOT_FOUND).isEmpty(), is(true));
	}

	@Test
	public void testGetFloatRequired_exists() {
		assertThat(systemUnderTest.getFloatRequired(FLOAT_KEY), is(FLOAT_VALUE));
	}

	@Test
	public void testGetFloatRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getFloatRequired(FLOAT_KEY_NOT_FOUND));
	}

	@Test
	public void testGetDate_exists() {
		assertThat(systemUnderTest.getDate(DATE_KEY, DATE_PATTERN), is(DATE_VALUE));
	}

	@Test
	public void testGetDate_notExists() {
		assertThat(systemUnderTest.getDate(DATE_KEY_NOT_FOUND, DATE_PATTERN), nullValue());
	}

	@Test
	public void testGetDateOptional_exists() {
		assertThat(systemUnderTest.getDateOptional(DATE_KEY, DATE_PATTERN).get(), is(DATE_VALUE));
	}

	@Test
	public void testGetDateOptional_notExists() {
		assertThat(systemUnderTest.getDateOptional(DATE_KEY_NOT_FOUND, DATE_PATTERN).isEmpty(), is(true));
	}

	@Test
	public void testGetDateRequired_exists() {
		assertThat(systemUnderTest.getDateRequired(DATE_KEY, DATE_PATTERN), is(DATE_VALUE));
	}

	@Test
	public void testGetDateRequired_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTest.getDateRequired(DATE_KEY_NOT_FOUND, DATE_PATTERN));
	}

	@Test
	public void testGetAppName_exists() {
		assertThat(systemUnderTest.getEnvironment().getAppName(), is(APP_NAME_VALUE));
	}

	@Test
	public void testGetAppName_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTestEmpty.getEnvironment().getAppName());
	}

	@Test
	public void testGetAppName_altKeyExists() {
		assertThat(systemUnderTestAltEnvKeys.getEnvironment().getAppNameKey(), is(APP_NAME_ALT_KEY));
		assertThat(systemUnderTestAltEnvKeys.getEnvironment().getAppName(), is(APP_NAME_VALUE));
	}

	@Test
	public void testGetAppName_setAltKeyNull() {
		Assertions.assertThrows(NullPointerException.class,
				() -> systemUnderTestAltEnvKeys.getEnvironment().setAppNameKey(null));
	}

	@Test
	public void testGetAppVersion_exists() {
		assertThat(systemUnderTest.getEnvironment().getAppVersion(), is(APP_VERSION_VALUE));
	}

	@Test
	public void testGetAppVersion_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTestEmpty.getEnvironment().getAppVersion());
	}

	@Test
	public void testGetAppVersion_altKeyExists() {
		assertThat(systemUnderTestAltEnvKeys.getEnvironment().getAppVersionKey(), is(APP_VERSION_ALT_KEY));
		assertThat(systemUnderTestAltEnvKeys.getEnvironment().getAppVersion(), is(APP_VERSION_VALUE));
	}

	@Test
	public void testGetAppVersion_setAltKeyNull() {
		Assertions.assertThrows(NullPointerException.class,
				() -> systemUnderTestAltEnvKeys.getEnvironment().setAppVersionKey(null));
	}

	@Test
	public void testGetActiveProfile_exists() {
		assertThat(systemUnderTest.getEnvironment().getActiveProfile(), is(ACTIVE_PROFILE_VALUE));
	}

	@Test
	public void testGetActiveProfile_notExists() {
		Assertions.assertThrows(PropertyNotFoundException.class,
				() -> systemUnderTestEmpty.getEnvironment().getActiveProfile());
	}

	@Test
	public void testGetActiveProfile_altKeyExists() {
		assertThat(systemUnderTestAltEnvKeys.getEnvironment().getActiveProfileKey(), is(ACTIVE_PROFILE_ALT_KEY));
		assertThat(systemUnderTestAltEnvKeys.getEnvironment().getActiveProfile(), is(ACTIVE_PROFILE_VALUE));
	}

	@Test
	public void testGetActiveProfile_setAltKeyNull() {
		Assertions.assertThrows(NullPointerException.class,
				() -> systemUnderTestAltEnvKeys.getEnvironment().setActiveProfileKey(null));
	}
}
