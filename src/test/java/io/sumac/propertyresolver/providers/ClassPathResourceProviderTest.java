package io.sumac.propertyresolver.providers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import io.sumac.propertyresolver.PropertyResolver;
import io.sumac.propertyresolver.PropertyResolverException;

public class ClassPathResourceProviderTest {

	@Test
	public void testGetString() {
		var sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties").build();
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
		var sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties").build();
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
		var sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties").build();
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
		var sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties").build();
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
		var sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties").build();
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
		var sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties").build();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isEmpty(), is(true));
		sut.refresh();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isEmpty(), is(true));
	}

	@Test
	public void testFileNotFound() {
		try {
			PropertyResolver.registerProviders().addClasspathPropertiesFile("missing_file.properties").build();
			fail("Expected PropertyResolverException but no Exceptions thrown.");
		} catch (Exception e) {
			assertThat(e, is(instanceOf(PropertyResolverException.class)));
			assertThat(e.getMessage(), is("File not found on classpath: 'missing_file.properties'"));
		}
	}

}
