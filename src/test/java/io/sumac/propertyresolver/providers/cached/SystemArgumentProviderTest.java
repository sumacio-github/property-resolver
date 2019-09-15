package io.sumac.propertyresolver.providers.cached;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.sumac.propertyresolver.PropertyResolver;

public class SystemArgumentProviderTest {

	@BeforeEach
	public void setUp() {
		System.setProperty("test.found.string", "hello world");
		System.setProperty("test.found.int", "32");
		System.setProperty("test.found.long", "64");
		System.setProperty("test.found.float", "1.1");
		System.setProperty("test.found.double", "2.2");
		System.setProperty("test.found.boolean", "true");
	}

	@AfterEach
	public void tearDown() {
		System.clearProperty("test.found.string");
		System.clearProperty("test.found.int");
		System.clearProperty("test.found.long");
		System.clearProperty("test.found.float");
		System.clearProperty("test.found.double");
		System.clearProperty("test.found.boolean");
	}

	@Test
	public void testGetString() {
		PropertyResolver sut = PropertyResolver.registerProviders().addSystemArguments().build();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("hello world"));
		assertThat(sut.getString("test.not_found.string").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("hello world"));
		assertThat(sut.getString("test.not_found.string").isPresent(), is(false));

	}

	@Test
	public void testGetBoolean() {
		PropertyResolver sut = PropertyResolver.registerProviders().addSystemArguments().build();
		assertThat(sut.getBoolean("test.found.boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test.found.boolean").get(), is(true));
		assertThat(sut.getBoolean("test.not_found.boolean").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getBoolean("test.found.boolean").isPresent(), is(true));
		assertThat(sut.getBoolean("test.found.boolean").get(), is(true));
		assertThat(sut.getBoolean("test.not_found.boolean").isPresent(), is(false));
	}

	@Test
	public void testGetInt() {
		PropertyResolver sut = PropertyResolver.registerProviders().addSystemArguments().build();
		assertThat(sut.getInt("test.found.int").isPresent(), is(true));
		assertThat(sut.getInt("test.found.int").get(), is(32));
		assertThat(sut.getInt("test.not_found.int").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getInt("test.found.int").isPresent(), is(true));
		assertThat(sut.getInt("test.found.int").get(), is(32));
		assertThat(sut.getInt("test.not_found.int").isPresent(), is(false));
	}

	@Test
	public void testGetLong() {
		PropertyResolver sut = PropertyResolver.registerProviders().addSystemArguments().build();
		assertThat(sut.getLong("test.found.long").isPresent(), is(true));
		assertThat(sut.getLong("test.found.long").get(), is(64L));
		assertThat(sut.getLong("test.not_found.long").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getLong("test.found.long").isPresent(), is(true));
		assertThat(sut.getLong("test.found.long").get(), is(64L));
		assertThat(sut.getLong("test.not_found.long").isPresent(), is(false));
	}

	@Test
	public void testGetFloat() {
		PropertyResolver sut = PropertyResolver.registerProviders().addSystemArguments().build();
		assertThat(sut.getFloat("test.found.float").isPresent(), is(true));
		assertThat(sut.getFloat("test.found.float").get(), is(1.1F));
		assertThat(sut.getFloat("test.not_found.float").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getFloat("test.found.float").isPresent(), is(true));
		assertThat(sut.getFloat("test.found.float").get(), is(1.1F));
		assertThat(sut.getFloat("test.not_found.float").isPresent(), is(false));
	}

	@Test
	public void testGetDouble() {
		PropertyResolver sut = PropertyResolver.registerProviders().addSystemArguments().build();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isPresent(), is(false));
	}

}
