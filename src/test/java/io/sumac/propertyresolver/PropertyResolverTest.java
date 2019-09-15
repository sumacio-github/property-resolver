package io.sumac.propertyresolver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class PropertyResolverTest {

	private static final Logger LOGGER = LogManager.getLogger(PropertyResolverTest.class);

	private BiConsumer<String, String> customLoggingInspector = (key, value) -> {
		LOGGER.debug("{}={}", key, value);
	};

	private Consumer<String> customLoggingPropertyNotFoundHandler = key -> {
		LOGGER.warn("{}=<NULL>", key);
	};

	private UnaryOperator<String> customToUpperCaseTransformer = s -> s.toUpperCase();

	@Test
	public void testGetString() {
		PropertyResolver sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("HELLO WORLD"));
		assertThat(sut.getString("test.not_found.string").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getString("test.found.string").isPresent(), is(true));
		assertThat(sut.getString("test.found.string").get(), is("HELLO WORLD"));
		assertThat(sut.getString("test.not_found.string").isPresent(), is(false));

	}

	@Test
	public void testGetBoolean() {
		PropertyResolver sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
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
		PropertyResolver sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
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
		PropertyResolver sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
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
		PropertyResolver sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
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
		PropertyResolver sut = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isPresent(), is(false));
		sut.refresh();
		assertThat(sut.getDouble("test.found.double").isPresent(), is(true));
		assertThat(sut.getDouble("test.found.double").get(), is(2.2));
		assertThat(sut.getDouble("test.not_found.double").isPresent(), is(false));
	}

}
