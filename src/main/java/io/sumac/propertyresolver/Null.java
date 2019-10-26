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
 * When present explicitly on a method parameter, the method should be able to
 * safely accept null as a valid input and should never throw a
 * {@code NullPointerException} if the value is null.
 * <p />
 * When present explicitly on a method then the developer should assume that the
 * annotated method could return null as a valid output. The developer should
 * perform a null check on the return object before any downstream processing to
 * avoid unexpected {@code NullPointerException}
 * 
 * @author ross
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, PARAMETER })
public @interface Null {

}
