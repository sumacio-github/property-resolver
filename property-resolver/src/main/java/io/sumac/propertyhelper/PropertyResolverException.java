package io.sumac.propertyhelper;

import java.io.IOException;
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

}