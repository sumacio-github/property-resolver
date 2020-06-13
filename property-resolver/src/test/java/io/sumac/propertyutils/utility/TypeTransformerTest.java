package io.sumac.propertyutils.utility;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
public class TypeTransformerTest {

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