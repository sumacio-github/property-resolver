package io.sumac.propertyresolver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.sql.SQLException;

public abstract class PropertyResolverException extends RuntimeException {

	private static final long serialVersionUID = 5679265155061006371L;

	public PropertyResolverException(String msg) {
		super(msg);
	}

	public PropertyResolverException(String msg, Throwable t) {
		super(msg, t);
	}

	public static PropertyResolverException errorReadingClasspathPropertyFile(String propertiesFile, IOException e) {
		return new PropertySourceNotFoundException("File not found on classpath: '" + propertiesFile + "'", e);
	}

	public static PropertyResolverException errorReadingClasspathPropertyFile(String propertiesFile) {
		return new PropertySourceNotFoundException("File not found on classpath: '" + propertiesFile + "'");
	}

	public static PropertyResolverException errorReadingPropertyFile(Path propertiesFile, IOException e) {
		return new PropertySourceNotFoundException("File not read: '" + propertiesFile + "'", e);
	}

	public static PropertyResolverException propertyNotFound(String name) {
		return new PropertyNotFoundException("Property not found: '" + name + "'");
	}

	static PropertyResolverException unsupportedType(Parameter parameter) {
		return new UnsupportedTypeException("Parameter type not supported: " + parameter.getType());
	}

	static PropertyResolverException unsupportedType(Field field) {
		return new UnsupportedTypeException("Filed type not supported: " + field.getType());
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

	static PropertyResolverException tooManySetterArgs(Method method) {
		return new BadSetterMethodException(
				"Too many arguments: " + method.getName() + ": " + method.getParameterCount());
	}

	static PropertyResolverException noSetterArgs(Method method) {
		return new BadSetterMethodException("No arguments: " + method.getName());
	}

	public static PropertyResolverException sqlError(SQLException e) {
		return new SqlErrorException("SQL error", e);
	}

	static class PropertySourceNotFoundException extends PropertyResolverException {

		private static final long serialVersionUID = -6823604987227128796L;

		private PropertySourceNotFoundException(String msg) {
			super(msg);
		}

		private PropertySourceNotFoundException(String msg, Throwable t) {
			super(msg, t);
		}
	}

	static class PropertyNotFoundException extends PropertyResolverException {

		private static final long serialVersionUID = 8351231596599069075L;

		private PropertyNotFoundException(String msg) {
			super(msg);
		}
	}

	static class UnsupportedTypeException extends PropertyResolverException {

		private static final long serialVersionUID = -810965606991101370L;

		private UnsupportedTypeException(String msg) {
			super(msg);
		}
	}

	static class BadConstructorException extends PropertyResolverException {

		private static final long serialVersionUID = 372303035607482593L;

		private BadConstructorException(String msg) {
			super(msg);
		}
	}

	static class BadSetterMethodException extends PropertyResolverException {

		private static final long serialVersionUID = -7296867680201227300L;

		private BadSetterMethodException(String msg) {
			super(msg);
		}
	}

	static class ReflectionErrorException extends PropertyResolverException {

		private static final long serialVersionUID = -1248202983084690657L;

		private ReflectionErrorException(String msg, Throwable t) {
			super(msg, t);
		}
	}

	static class SqlErrorException extends PropertyResolverException {

		private static final long serialVersionUID = 6566866076070089166L;

		private SqlErrorException(String msg, Throwable t) {
			super(msg, t);
		}
	}

}
