package io.sumac.propertyresolver;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This method is used for documentation hints in this API. There is no logic in
 * this API to enforce logic when this annotation is or is not present so it is
 * up to the developer to understand proper usage.
 * <p />
 * When present explicitly on a method parameter, the method should check
 * whether the parameter is null and should throw a {@code NullPointerException}
 * if the value is null. Developers using methods with annotated parameters
 * should perform a null check prior to calling the method.
 * <p />
 * When present explicitly on a method then the developer should be able to
 * safely assume that this method is expected to never return a null value by
 * contract. If the method ever returns null then that should be considered a
 * defect or improper usage of this annotation.
 * 
 * @author ross
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, PARAMETER })
public @interface NotNull {

}
