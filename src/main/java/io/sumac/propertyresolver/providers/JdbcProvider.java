package io.sumac.propertyresolver.providers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import io.sumac.propertyresolver.PropertyResolverException;

public class JdbcProvider extends RefreshableProvider {

	private final DataSource dataSource;
	private final String username;
	private final String password;
	private final String table;

	public JdbcProvider(DataSource dataSource, String username, String password, String table) {
		this.dataSource = dataSource;
		this.username = username;
		this.password = password;
		this.table = table;
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		var props = new Properties();
		try (final Connection connection = dataSource.getConnection(username, password);
				final Statement statement = connection.createStatement();
				final ResultSet resultSet = statement.executeQuery(String.format("select * from %s t", table));) {
			while (resultSet.next()) {
				props.put(resultSet.getString(1), resultSet.getString(2));
			}
			return props;
		} catch (SQLException e) {
			throw PropertyResolverException.sqlError(e);
		}
	}

}
