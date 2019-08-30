package io.sumac.propertyresolver.providers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.sumac.propertyresolver.PropertyResolver;

@ExtendWith(EnvironmentVariableProviderTest.class)
public class EnvironmentVariableProviderTest implements ExecutionCondition {

	@Test
	public void testGetString() {
		var sut = PropertyResolver.registerProviders().addEnvironmentVariables().build();
		assertThat(sut.getString("test_found_string").isPresent(), is(true));
		assertThat(sut.getString("test_found_string").get(), is("hello world"));
		assertThat(sut.getString("test_not_found_string").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getString("test_found_string").isPresent(), is(true));
		assertThat(sut.getString("test_found_string").get(), is("hello world"));
		assertThat(sut.getString("test_not_found_string").isEmpty(), is(true));

	}

	@Test
	public void testGetBoolean() {
		var sut = PropertyResolver.registerProviders().addEnvironmentVariables().build();
		assertThat(sut.getBoolean("test_found_boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test_found_boolean").get(), is(true));
		assertThat(sut.getBoolean("test_not_found_boolean").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getBoolean("test_found_boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test_found_boolean").get(), is(true));
		assertThat(sut.getBoolean("test_not_found_boolean").isEmpty(), is(true));
	}

	@Test
	public void testGetInt() {
		var sut = PropertyResolver.registerProviders().addEnvironmentVariables().build();
		assertThat(sut.getInt("test_found_int").isPresent(), is(true));
		assertThat(sut.getInt("test_found_int").get(), is(32));
		assertThat(sut.getInt("test_not_found_int").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getInt("test_found_int").isPresent(), is(true));
		assertThat(sut.getInt("test_found_int").get(), is(32));
		assertThat(sut.getInt("test_not_found_int").isEmpty(), is(true));
	}

	@Test
	public void testGetLong() {
		var sut = PropertyResolver.registerProviders().addEnvironmentVariables().build();
		assertThat(sut.getLong("test_found_long").isPresent(), is(true));
		assertThat(sut.getLong("test_found_long").get(), is(64L));
		assertThat(sut.getLong("test_not_found_long").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getLong("test_found_long").isPresent(), is(true));
		assertThat(sut.getLong("test_found_long").get(), is(64L));
		assertThat(sut.getLong("test_not_found_long").isEmpty(), is(true));
	}

	@Test
	public void testGetFloat() {
		var sut = PropertyResolver.registerProviders().addEnvironmentVariables().build();
		assertThat(sut.getFloat("test_found_float").isPresent(), is(true));
		assertThat(sut.getFloat("test_found_float").get(), is(1.1F));
		assertThat(sut.getFloat("test_not_found_float").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getFloat("test_found_float").isPresent(), is(true));
		assertThat(sut.getFloat("test_found_float").get(), is(1.1F));
		assertThat(sut.getFloat("test_not_found_float").isEmpty(), is(true));
	}

	@Test
	public void testGetDouble() {
		var sut = PropertyResolver.registerProviders().addEnvironmentVariables().build();
		assertThat(sut.getDouble("test_found_double").isPresent(), is(true));
		assertThat(sut.getDouble("test_found_double").get(), is(2.2));
		assertThat(sut.getDouble("test_not_found_double").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getDouble("test_found_double").isPresent(), is(true));
		assertThat(sut.getDouble("test_found_double").get(), is(2.2));
		assertThat(sut.getDouble("test_not_found_double").isEmpty(), is(true));
	}

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		boolean hasStringProp = System.getenv("test_found_string") != null;
		boolean hasIntProp = System.getenv("test_found_int") != null;
		boolean hasLongProp = System.getenv("test_found_long") != null;
		boolean hasFloatProp = System.getenv("test_found_float") != null;
		boolean hasDoubleProp = System.getenv("test_found_double") != null;
		boolean hasBooleanProp = System.getenv("test_found_boolean") != null;
		boolean condition = hasStringProp && hasIntProp && hasLongProp && hasFloatProp && hasDoubleProp
				&& hasBooleanProp;
		if (condition) {
			return ConditionEvaluationResult.enabled("environment variables set");
		} else {
			return ConditionEvaluationResult.disabled("environment variables not set");
		}
	}

}
