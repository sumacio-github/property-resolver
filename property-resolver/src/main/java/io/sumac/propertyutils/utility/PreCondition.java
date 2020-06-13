package io.sumac.propertyutils.utility;

import io.sumac.propertyutils.annotations.NotNull;

import java.util.Objects;

/**
 * This PreCondition class is intended to be used in conjunction with the {@link NotNull} annotation to validate that
 * the contract of a method is being followed properly. In this case a pre-condition failure is likely the result of improper usage or contract conditions
 * not being satisfied and should be investigated as a potential defect.
 * <p />
 * This PreCondition class is <em>not</em> intended to be used to validate end user input commands or arguments.
 *
 * @author ross
 */
public final class PreCondition {
    public final static class Result {
        private static final String NOT_NULL_DEFAULT_MESSAGE = "[Pre-Condition Failure]: The result is null!";

        public static <T> T notNull(T obj, String msg) {
            if (Objects.isNull(obj)) {
                throw new IllegalStateException(msg);
            }
            return obj;
        }

        public static <T> T notNull(T obj) {
            notNull(obj, NOT_NULL_DEFAULT_MESSAGE);
            return obj;
        }
    }

    public final static class Parameter {
        private static final String NOT_NULL_DEFAULT_MESSAGE = "[Pre-Condition Failure]: The parameter is null!";

        public static void notNull(Object obj, String msg) {
            if (Objects.isNull(obj)) {
                throw new IllegalArgumentException(msg);
            }
        }

        public static void notNull(Object obj) {
            notNull(obj, NOT_NULL_DEFAULT_MESSAGE);
        }
    }
}
