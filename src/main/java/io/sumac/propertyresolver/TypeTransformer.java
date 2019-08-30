package io.sumac.propertyresolver;

import java.util.Objects;

public final class TypeTransformer {
	private TypeTransformer() {
	}

	public static int toInt(String value) {
		rejectIfNull(value);
		return Integer.valueOf(value);
	}

	public static long toLong(String value) {
		rejectIfNull(value);
		return Long.valueOf(value);
	}

	public static double toDouble(String value) {
		rejectIfNull(value);
		return Double.valueOf(value);
	}

	public static float toFloat(String value) {
		rejectIfNull(value);
		return Float.valueOf(value);
	}

	public static boolean toBoolean(String value) {
		rejectIfNull(value);
		return Boolean.valueOf(value);
	}

	private static void rejectIfNull(Object value) {
		if (Objects.isNull(value)) {
			throw new NullPointerException("Can't transform null");
		}
	}

	public static boolean isString(Class<?> type) {
		return String.class.equals(type);
	}

	public static boolean isString(Object obj) {
		return isString(obj.getClass());
	}

	public static boolean isInt(Class<?> type) {
		return Integer.class.equals(type) || int.class.equals(type);
	}

	public static boolean isInt(Object obj) {
		return isInt(obj.getClass());
	}

	public static boolean isLong(Class<?> type) {
		return Long.class.equals(type) || long.class.equals(type);
	}

	public static boolean isLong(Object obj) {
		return isLong(obj.getClass());
	}

	public static boolean isDouble(Class<?> type) {
		return Double.class.equals(type) || double.class.equals(type);
	}

	public static boolean isDouble(Object obj) {
		return isDouble(obj.getClass());
	}

	public static boolean isFloat(Class<?> type) {
		return Float.class.equals(type) || float.class.equals(type);
	}

	public static boolean isFloat(Object obj) {
		return isFloat(obj.getClass());
	}

	public static boolean isBoolean(Class<?> type) {
		return Boolean.class.equals(type) || boolean.class.equals(type);
	}

	public static boolean isBoolean(Object obj) {
		return isBoolean(obj.getClass());
	}

}