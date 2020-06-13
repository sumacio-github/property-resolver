package io.sumac.propertyutils.utility;

import java.util.Date;

public class TypeTransformer {
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

    public static boolean isDate(Class<?> type) {
        return Date.class.equals(type);
    }

    public static boolean isDate(Object obj) {
        return isDate(obj.getClass());
    }
}
