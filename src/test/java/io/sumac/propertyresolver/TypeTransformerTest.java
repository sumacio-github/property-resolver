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

	@Test
	public void testIsString() {
		String input = "hello world";
		assertThat(TypeTransformer.isString(String.class), is(true));
		assertThat(TypeTransformer.isString(Object.class), is(false));
		assertThat(TypeTransformer.isString(input), is(true));
		assertThat(TypeTransformer.isString(new Object()), is(false));
	}

	@Test
	public void testIsInt() {
		int primitiveInput = 32;
		Integer objectInput = Integer.valueOf(32);
		byte invalidPrimitive = 1;
		assertThat(TypeTransformer.isInt(Integer.class), is(true));
		assertThat(TypeTransformer.isInt(int.class), is(true));
		assertThat(TypeTransformer.isInt(Object.class), is(false));
		assertThat(TypeTransformer.isInt(primitiveInput), is(true));
		assertThat(TypeTransformer.isInt(objectInput), is(true));
		assertThat(TypeTransformer.isInt(new Object()), is(false));
		assertThat(TypeTransformer.isInt(invalidPrimitive), is(false));
	}

	@Test
	public void testIsLong() {
		long primitiveInput = 64L;
		Long objectInput = Long.valueOf(64L);
		byte invalidPrimitive = 1;
		assertThat(TypeTransformer.isLong(Long.class), is(true));
		assertThat(TypeTransformer.isLong(long.class), is(true));
		assertThat(TypeTransformer.isLong(Object.class), is(false));
		assertThat(TypeTransformer.isLong(primitiveInput), is(true));
		assertThat(TypeTransformer.isLong(objectInput), is(true));
		assertThat(TypeTransformer.isLong(new Object()), is(false));
		assertThat(TypeTransformer.isLong(invalidPrimitive), is(false));
	}

	@Test
	public void testIsFloat() {
		float primitiveInput = 1.1F;
		Float objectInput = Float.valueOf(1.1F);
		byte invalidPrimitive = 1;
		assertThat(TypeTransformer.isFloat(Float.class), is(true));
		assertThat(TypeTransformer.isFloat(float.class), is(true));
		assertThat(TypeTransformer.isFloat(Object.class), is(false));
		assertThat(TypeTransformer.isFloat(primitiveInput), is(true));
		assertThat(TypeTransformer.isFloat(objectInput), is(true));
		assertThat(TypeTransformer.isFloat(new Object()), is(false));
		assertThat(TypeTransformer.isFloat(invalidPrimitive), is(false));
	}

	@Test
	public void testIsDouble() {
		double primitiveInput = 2.2;
		Double objectInput = Double.valueOf(2.2);
		byte invalidPrimitive = 1;
		assertThat(TypeTransformer.isDouble(Double.class), is(true));
		assertThat(TypeTransformer.isDouble(double.class), is(true));
		assertThat(TypeTransformer.isDouble(Object.class), is(false));
		assertThat(TypeTransformer.isDouble(primitiveInput), is(true));
		assertThat(TypeTransformer.isDouble(objectInput), is(true));
		assertThat(TypeTransformer.isDouble(new Object()), is(false));
		assertThat(TypeTransformer.isDouble(invalidPrimitive), is(false));
	}

	@Test
	public void testIsBoolean() {
		boolean primitiveInput = true;
		Boolean objectInput = Boolean.valueOf(true);
		byte invalidPrimitive = 1;
		assertThat(TypeTransformer.isBoolean(Boolean.class), is(true));
		assertThat(TypeTransformer.isBoolean(boolean.class), is(true));
		assertThat(TypeTransformer.isBoolean(Object.class), is(false));
		assertThat(TypeTransformer.isBoolean(primitiveInput), is(true));
		assertThat(TypeTransformer.isBoolean(objectInput), is(true));
		assertThat(TypeTransformer.isBoolean(new Object()), is(false));
		assertThat(TypeTransformer.isBoolean(invalidPrimitive), is(false));
	}

}
