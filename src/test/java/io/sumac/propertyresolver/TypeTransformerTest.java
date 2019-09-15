package io.sumac.propertyresolver;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTransformerTest {

	@Test
	public void testToInt() {
		String input = "32";
		Integer output = TypeTransformer.toInt(input);
		assertThat(output, is(instanceOf(Integer.class)));
		assertThat(output, is(32));
	}

	@Test
	public void testToLong() {
		String input = "64";
		Long output = TypeTransformer.toLong(input);
		assertThat(output, is(instanceOf(Long.class)));
		assertThat(output, is(64L));
	}

	@Test
	public void testToFloat() {
		String input = "1.1";
		Float output = TypeTransformer.toFloat(input);
		assertThat(output, is(instanceOf(Float.class)));
		assertThat(output, is(1.1F));
	}

	@Test
	public void testToDouble() {
		String input = "2.2";
		Double output = TypeTransformer.toDouble(input);
		assertThat(output, is(instanceOf(Double.class)));
		assertThat(output, is(2.2));
	}

	@Test
	public void testToBoolean() {
		String input = "true";
		Boolean output = TypeTransformer.toBoolean(input);
		assertThat(output, is(instanceOf(Boolean.class)));
		assertThat(output, is(true));
	}

	@Test
	public void testToInt_null() {
		Assertions.assertThrows(NullPointerException.class, () -> TypeTransformer.toInt(null));
	}

	@Test
	public void testToLong_null() {
		Assertions.assertThrows(NullPointerException.class, () -> TypeTransformer.toLong(null));
	}

	@Test
	public void testToFloat_null() {
		Assertions.assertThrows(NullPointerException.class, () -> TypeTransformer.toFloat(null));
	}

	@Test
	public void testToDouble_null() {
		Assertions.assertThrows(NullPointerException.class, () -> TypeTransformer.toDouble(null));
	}

	@Test
	public void testToBoolean_null() {
		Assertions.assertThrows(NullPointerException.class, () -> TypeTransformer.toBoolean(null));
	}

}
