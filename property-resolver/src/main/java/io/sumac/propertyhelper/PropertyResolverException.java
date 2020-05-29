package io.sumac.propertyhelper;

import java.io.IOException;

public abstract class PropertyResolverException extends RuntimeException {

	private static final long serialVersionUID = 5679265155061006371L;

	protected PropertyResolverException(String msg) {
		super(msg);
	}

	protected PropertyResolverException(String msg, Throwable t) {
		super(msg, t);
	}

	public static PropertyResolverException from(IOException e) {
		return new UnexpectedIOException("An unexpected " + e.getClass().getName() + " occurred: " + e.getMessage(), e);
	}

	public static PropertyResolverException propertyNotFound(String propertyKey) {
		return new PropertyNotFoundException("Property not found: '" + propertyKey + "'");
	}

	static class UnexpectedIOException extends PropertyResolverException {

		private static final long serialVersionUID = -6823604987227128796L;

		private UnexpectedIOException(String msg, Throwable t) {
			super(msg, t);
		}
	}
	
	static class PropertyNotFoundException extends PropertyResolverException {

		private static final long serialVersionUID = 2032509051450805671L;

		protected PropertyNotFoundException(String msg) {
			super(msg);
		}
		
	}

}