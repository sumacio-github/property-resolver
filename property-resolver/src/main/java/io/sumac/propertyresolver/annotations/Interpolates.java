package io.sumac.propertyresolver.annotations;

import java.lang.annotation.*;

/**
 * This annotation is for documentation purposes.
 * Methods annotated with Interpolates will perform property substitution or interpolation,
 * replacing placeholder key strings contained inside '${' and '}' with their corresponding values.
 * For example consider the following property file:
 * <pre>
 * firstName=Bill
 * lastName=Dent
 * fullName=${lastName}, ${firstName}
 * </pre>
 * When using interpolation, a call to read the property '{@code fullName}' will yield the result
 * <pre>
 * Dent, Bill
 * </pre>
 *
 * @author ross
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface Interpolates {
}
