package io.sumac.propertyresolver.providers.dynamic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import io.sumac.propertyresolver.PropertyResolverException;
import io.sumac.propertyresolver.providers.PropertiesProvider;

public class JdbcProvider extends PropertiesProvider {
	private final DataSource dataSource;
	private final String table;
	private final String keyColumnName;
	private final String valueColumnName;

	public JdbcProvider(DataSource dataSource, String table, String keyColumnName, String valueColumnName) {
		this.dataSource = dataSource;
		this.table = table;
		this.keyColumnName = keyColumnName;
		this.valueColumnName = valueColumnName;
	}

	@Override
	public Optional<String> getString(String key) {
		try (final Connection connection = dataSource.getConnection();
				final PreparedStatement statement = connection.prepareStatement(
						String.format("select %s from %s t where %s = ?", valueColumnName, table, keyColumnName))) {
			statement.setString(0, key);
			try (final ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(resultSet.getString(1));
				} else {
					return Optional.empty();
				}
			}
		} catch (SQLException e) {
			throw PropertyResolverException.sqlError(e);
		}
	}
}
