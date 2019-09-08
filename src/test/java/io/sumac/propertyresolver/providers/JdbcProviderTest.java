package io.sumac.propertyresolver.providers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	private Statement statement;
	private ResultSet resultSet;

	@BeforeEach
	public void setUp() throws SQLException {
		dataSource = Mockito.mock(DataSource.class);
		connection = Mockito.mock(Connection.class);
		statement = Mockito.mock(Statement.class);
		resultSet = Mockito.mock(ResultSet.class);
		Mockito.when(dataSource.getConnection()).thenReturn(connection);
		Mockito.when(connection.createStatement()).thenReturn(statement);
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true)
				.thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true)
				.thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(resultSet.getString(1)).thenReturn("test.found.string").thenReturn("test.found.boolean")
				.thenReturn("test.found.int").thenReturn("test.found.long").thenReturn("test.found.float")
				.thenReturn("test.found.double").thenReturn("test.found.string").thenReturn("test.found.boolean")
				.thenReturn("test.found.int").thenReturn("test.found.long").thenReturn("test.found.float")
				.thenReturn("test.found.double");
		Mockito.when(resultSet.getString(2)).thenReturn("hello world").thenReturn("true").thenReturn("32")
				.thenReturn("64").thenReturn("1.1").thenReturn("2.2").thenReturn("hello world").thenReturn("true")
				.thenReturn("32").thenReturn("64").thenReturn("1.1").thenReturn("2.2");
	}

	@Test
	public void testGetString() {
		var sut = PropertyResolver.registerProviders().addJdbcTable(dataSource, "table").build();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("hello world"));
		assertThat(sut.getString("test.not_found.string").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("hello world"));
		assertThat(sut.getString("test.not_found.string").isEmpty(), is(true));

	}

	@Test
	public void testGetBoolean() {
		var sut = PropertyResolver.registerProviders().addJdbcTable(dataSource, "table").build();
		assertThat(sut.getBoolean("test.found.boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test.found.boolean").get(), is(true));
		assertThat(sut.getBoolean("test.not_found.boolean").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getBoolean("test.found.boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test.found.boolean").get(), is(true));
		assertThat(sut.getBoolean("test.not_found.boolean").isEmpty(), is(true));
	}

	@Test
	public void testGetInt() {
		var sut = PropertyResolver.registerProviders().addJdbcTable(dataSource, "table").build();
		assertThat(sut.getInt("test.found.int").isPresent(), is(true));
		assertThat(sut.getInt("test.found.int").get(), is(32));
		assertThat(sut.getInt("test.not_found.int").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getInt("test.found.int").isPresent(), is(true));
		assertThat(sut.getInt("test.found.int").get(), is(32));
		assertThat(sut.getInt("test.not_found.int").isEmpty(), is(true));
	}

	@Test
	public void testGetLong() {
		var sut = PropertyResolver.registerProviders().addJdbcTable(dataSource, "table").build();
		assertThat(sut.getLong("test.found.long").isPresent(), is(true));
		assertThat(sut.getLong("test.found.long").get(), is(64L));
		assertThat(sut.getLong("test.not_found.long").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getLong("test.found.long").isPresent(), is(true));
		assertThat(sut.getLong("test.found.long").get(), is(64L));
		assertThat(sut.getLong("test.not_found.long").isEmpty(), is(true));
	}

	@Test
	public void testGetFloat() {
		var sut = PropertyResolver.registerProviders().addJdbcTable(dataSource, "table").build();
		assertThat(sut.getFloat("test.found.float").isPresent(), is(true));
		assertThat(sut.getFloat("test.found.float").get(), is(1.1F));
		assertThat(sut.getFloat("test.not_found.float").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getFloat("test.found.float").isPresent(), is(true));
		assertThat(sut.getFloat("test.found.float").get(), is(1.1F));
		assertThat(sut.getFloat("test.not_found.float").isEmpty(), is(true));
	}

	@Test
	public void testGetDouble() {
		var sut = PropertyResolver.registerProviders().addJdbcTable(dataSource, "table").build();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isEmpty(), is(true));
	}

	@Test
	public void testConnectionError() throws SQLException {
		DataSource ds = Mockito.mock(DataSource.class);
		Mockito.when(ds.getConnection()).thenThrow(new SQLException("connection failed"));
		try {
			PropertyResolver.registerProviders().addJdbcTable(ds, "table");
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
		Statement stmt = Mockito.mock(Statement.class);
		Mockito.when(ds.getConnection()).thenReturn(conn);
		Mockito.when(conn.createStatement()).thenReturn(stmt);
		Mockito.when(stmt.executeQuery(Mockito.anyString())).thenThrow(new SQLException("query failed"));
		try {
			PropertyResolver.registerProviders().addJdbcTable(ds, "table");
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
		Statement stmt = Mockito.mock(Statement.class);
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(ds.getConnection()).thenReturn(conn);
		Mockito.when(conn.createStatement()).thenReturn(stmt);
		Mockito.doAnswer(new Answer<ResultSet>() {

			@Override
			public ResultSet answer(InvocationOnMock invocation) throws Throwable {
				String query = invocation.getArgument(0, String.class);
				assertThat(query, is("select * from table t"));
				return rs;
			}

		}).when(stmt).executeQuery(Mockito.anyString());
		Mockito.when(rs.next()).thenReturn(false);
		PropertyResolver.registerProviders().addJdbcTable(ds, "table");

	}

}
