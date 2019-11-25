package io.sumac.propertyresolver;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

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

	public static PropertyResolverException from(JsonProcessingException e) {
		return new UnexpectedIOException(
				"An unexpected parsing/IO " + e.getClass().getName() + " occurred: " + e.getMessage(), e);
	}

	static class UnexpectedIOException extends PropertyResolverException {

		private static final long serialVersionUID = -6823604987227128796L;

		private UnexpectedIOException(String msg, Throwable t) {
			super(msg, t);
		}
	}

}