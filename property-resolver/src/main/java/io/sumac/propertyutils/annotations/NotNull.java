package io.sumac.propertyutils.annotations;

import io.sumac.propertyutils.utility.PreCondition;

import java.lang.annotation.*;

/**
 * This annotation is for documentation purposes.
 * A method annotated with NotNull is <em>forbidden</em> to return a {@code null} value under any circumstance.
 * A parameter annotated with NotNull is not expected to handle {@code null} as a valid input and may result in a {@code NullPointerException} or {@code IllegalArgumentException} if a null value is given.
 *
 * @author ross
 * @see PreCondition
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface NotNull {
}
