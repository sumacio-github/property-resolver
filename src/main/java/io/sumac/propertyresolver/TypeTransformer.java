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

}