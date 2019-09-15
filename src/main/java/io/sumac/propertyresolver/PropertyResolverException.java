package io.sumac.propertyresolver;

import java.io.IOException;
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

	public static PropertyResolverException errorReadingPropertyFile(String propertiesFile, IOException e) {
		return new PropertySourceNotFoundException("File not read: '" + propertiesFile + "'", e);
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

	static class SqlErrorException extends PropertyResolverException {

		private static final long serialVersionUID = 6566866076070089166L;

		private SqlErrorException(String msg, Throwable t) {
			super(msg, t);
		}
	}

}
