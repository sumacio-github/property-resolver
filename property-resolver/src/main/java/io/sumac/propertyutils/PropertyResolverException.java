package io.sumac.propertyutils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public abstract class PropertyResolverException extends RuntimeException {

    protected PropertyResolverException(String msg) {
        super(msg);
    }

    protected PropertyResolverException(String msg, Throwable t) {
        super(msg, t);
    }

    public static PropertyResolverException from(IOException e) {
        return new UnexpectedIOException("An unexpected " + e.getClass().getName() + " occurred: " + e.getMessage(), e);
    }

    public static PropertyResolverException propertiesNotFound(List<String> propertyKeys) {
        return new PropertyNotFoundException("Property not found: '" + propertyKeys + "'");
    }

    public static PropertyResolverException propertyNotFound(String... propertyKeys) {
        return propertiesNotFound(Arrays.asList(propertyKeys));
    }

    public static PropertyResolverException validationFailure(Throwable t) {
        return new ValidationException("Validation error", t);
    }

    public static PropertyResolverException keyNestedInsideKey() {
        return new InterpolationErrorException("Interpolation error: Cannot nest a placeholder key inside of a property key.");
    }

    public static PropertyResolverException keyNestedInsideDefault() {
        return new InterpolationErrorException("Interpolation error: Cannot nest a placeholder key inside of a default value.");
    }

    static PropertyResolverException unsupportedType(Field field) {
        return new UnsupportedTypeException("Field type not supported: " + field.getType());
    }

    static PropertyResolverException unsupportedType(Parameter parameter) {
        return new UnsupportedTypeException("Parameter type not supported: " + parameter.getType());
    }

    static PropertyResolverException tooManySetterArgs(Method method) {
        return new BadSetterMethodException(
                "Too many arguments: " + method.getName() + ": " + method.getParameterCount());
    }

    static PropertyResolverException noSetterArgs(Method method) {
        return new BadSetterMethodException("No arguments: " + method.getName());
    }

    static PropertyResolverException tooManyConstructors(int count) {
        return new BadConstructorException("Too many constructors: " + count);
    }

    static PropertyResolverException constructorArgNotAnnotated(Parameter parameter) {
        return new BadConstructorException("Parameter not annotated: " + parameter.getName());
    }

    static PropertyResolverException wrapCheckedReflectionExceptions(Exception e) {
        return new ReflectionErrorException("Reflection error", e);
    }

    static class UnexpectedIOException extends PropertyResolverException {
        private UnexpectedIOException(String msg, Throwable t) {
            super(msg, t);
        }
    }

    static class PropertyNotFoundException extends PropertyResolverException {
        protected PropertyNotFoundException(String msg) {
            super(msg);
        }
    }

    static class ValidationException extends PropertyResolverException {
        protected ValidationException(String msg, Throwable t) {
            super(msg, t);
        }
    }

    static class InterpolationErrorException extends PropertyResolverException {
        protected InterpolationErrorException(String msg) {
            super(msg);
        }
    }

    static class UnsupportedTypeException extends PropertyResolverException {
        private UnsupportedTypeException(String msg) {
            super(msg);
        }
    }

    static class BadSetterMethodException extends PropertyResolverException {
        private BadSetterMethodException(String msg) {
            super(msg);
        }
    }

    static class BadConstructorException extends PropertyResolverException {
        private BadConstructorException(String msg) {
            super(msg);
        }
    }

    static class ReflectionErrorException extends PropertyResolverException {
        private ReflectionErrorException(String msg, Throwable t) {
            super(msg, t);
        }
    }

}