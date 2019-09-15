package io.sumac.propertyresolver.providers.dynamic;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.sumac.propertyresolver.PropertyResolver;
import io.sumac.propertyresolver.PropertyResolverException;

public class JdbcProviderTest {

	private DataSource dataSource;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	@BeforeEach
	public void setUp() throws SQLException {
		dataSource = Mockito.mock(DataSource.class);
		connection = Mockito.mock(Connection.class);
		statement = Mockito.mock(PreparedStatement.class);
		resultSet = Mockito.mock(ResultSet.class);
		Mockito.when(dataSource.getConnection()).thenReturn(connection);
		Mockito.when(connection.prepareStatement("select value from table t where key = ?")).thenReturn(statement);
		Mockito.when(statement.executeQuery()).thenReturn(resultSet);
	}

	@Test
	public void testGetString() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(true);
		Mockito.when(resultSet.getString(1)).thenReturn("hello world");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("hello world"));
	}

	@Test
	public void testGetStringNotFound() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(false);
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getString("test.not_found.string").isPresent(), is(false));
	}

	@Test
	public void testGetBoolean() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(true);
		Mockito.when(resultSet.getString(1)).thenReturn("true");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getBoolean("test.found.boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test.found.boolean").get(), is(true));
	}

	@Test
	public void testGetBooleanNotFound() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(false);
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getBoolean("test.not_found.boolean").isPresent(), is(false));
	}

	@Test
	public void testGetInt() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(true);
		Mockito.when(resultSet.getString(1)).thenReturn("32");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getInt("test.found.int").isPresent(), is(true));
		assertThat(sut.getInt("test.found.int").get(), is(32));
	}

	@Test
	public void testGetIntNotFound() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(false);
		Mockito.when(resultSet.getString(1)).thenReturn("32");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getInt("test.not_found.int").isPresent(), is(false));
	}

	@Test
	public void testGetLong() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(true);
		Mockito.when(resultSet.getString(1)).thenReturn("64");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getLong("test.found.long").isPresent(), is(true));
		assertThat(sut.getLong("test.found.long").get(), is(64L));
	}

	@Test
	public void testGetLongNotFound() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(false);
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getLong("test.not_found.long").isPresent(), is(false));
	}

	@Test
	public void testGetFloat() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(true);
		Mockito.when(resultSet.getString(1)).thenReturn("1.1");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getFloat("test.found.float").isPresent(), is(true));
		assertThat(sut.getFloat("test.found.float").get(), is(1.1F));
	}

	@Test
	public void testGetFloatNotFound() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(false);
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getFloat("test.not_found.float").isPresent(), is(false));
	}

	@Test
	public void testGetDouble() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(true);
		Mockito.when(resultSet.getString(1)).thenReturn("2.2");
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
	}

	@Test
	public void testGetDoubleNotFound() throws SQLException {
		Mockito.when(resultSet.next()).thenReturn(false);
		PropertyResolver sut = PropertyResolver.registerProviders().addJdbcLookup(dataSource, "table", "key", "value")
				.build();
		assertThat(sut.getDouble("test.not_found.double").isPresent(), is(false));
	}

	@Test
	public void testConnectionError() throws SQLException {
		DataSource ds = Mockito.mock(DataSource.class);
		Mockito.when(ds.getConnection()).thenThrow(new SQLException("connection failed"));
		try {
			PropertyResolver.registerProviders().addJdbcLookup(ds, "table", "key", "value").build()
					.getString("test.found.string");
			fail("Expected PropertyResolverException but no Exceptions thrown.");
		} catch (Exception e) {
			assertThat(e, is(instanceOf(PropertyResolverException.class)));
			assertThat(e.getMessage(), is("SQL error"));
			assertThat(e.getCause(), is(instanceOf(SQLException.class)));
			assertThat(e.getCause().getMessage(), is("connection failed"));
		}
	}

	@Test
	public void testQueryError() throws SQLException {
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
		Mockito.when(ds.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement("select value from table t where key = ?")).thenReturn(stmt);
		Mockito.when(stmt.executeQuery()).thenThrow(new SQLException("query failed"));
		try {
			PropertyResolver.registerProviders().addJdbcLookup(ds, "table", "key", "value").build()
					.getString("test.found.string");
			fail("Expected PropertyResolverException but no Exceptions thrown.");
		} catch (Exception e) {
			assertThat(e, is(instanceOf(PropertyResolverException.class)));
			assertThat(e.getMessage(), is("SQL error"));
			assertThat(e.getCause(), is(instanceOf(SQLException.class)));
			assertThat(e.getCause().getMessage(), is("query failed"));
		}
	}

	@Test
	public void testInputs() throws SQLException {
		DataSource ds = Mockito.mock(DataSource.class);
		Connection conn = Mockito.mock(Connection.class);
		PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(ds.getConnection()).thenReturn(conn);
		Mockito.doAnswer(new Answer<PreparedStatement>() {

			@Override
			public PreparedStatement answer(InvocationOnMock invocation) throws Throwable {
				String query = invocation.getArgument(0, String.class);
				assertThat(query, is("select value from table t where key = ?"));
				return stmt;
			}

		}).when(conn).prepareStatement(Mockito.anyString());
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(false);
		PropertyResolver.registerProviders().addJdbcLookup(ds, "table", "key", "value").build()
				.getString("test.found.string");

	}
}
