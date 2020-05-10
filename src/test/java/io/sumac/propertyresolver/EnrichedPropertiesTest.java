package io.sumac.propertyresolver;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

public class EnrichedPropertiesTest extends AbstractEnrichedPropertyResolverTest {

	@BeforeEach
	public void setUp() throws JsonProcessingException, IOException {
		systemUnderTest = new EnrichedProperties();
		systemUnderTest.put(STRING_KEY, STRING_VALUE);
		systemUnderTest.put(INT_KEY, Integer.valueOf(INT_VALUE).toString());
		systemUnderTest.put(LONG_KEY, Long.valueOf(LONG_VALUE).toString());
		systemUnderTest.put(DOUBLE_KEY, Double.valueOf(DOUBLE_VALUE).toString());
		systemUnderTest.put(FLOAT_KEY, Float.valueOf(FLOAT_VALUE).toString());
		systemUnderTest.put(BOOLEAN_KEY, Boolean.TRUE.toString());
		systemUnderTest.put(DATE_KEY, new SimpleDateFormat(DATE_PATTERN).format(DATE_VALUE));
	}

}
