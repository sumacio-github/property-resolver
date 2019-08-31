package io.sumac.propertyresolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import io.sumac.propertyresolver.sample.Model1;
import io.sumac.propertyresolver.sample.Model2;
import io.sumac.propertyresolver.sample.Model3;
import io.sumac.propertyresolver.sample.Model4;
import io.sumac.propertyresolver.sample.Model5;
import io.sumac.propertyresolver.sample.Model6;
import io.sumac.propertyresolver.sample.Model7;
import io.sumac.propertyresolver.sample.Model8;
import io.sumac.propertyresolver.sample.Model9;

public class PropertyResolverTest {

	private static final Logger LOGGER = Logger.getLogger(PropertyResolverTest.class.getName());

	private BiConsumer<String, Optional<String>> customInspector = (key, value) -> {
		LOGGER.info(String.format("%s=%s", key, value.isPresent() ? value.get() : "<NULL>"));
	};

	@Test
	public void toTest_fields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = systemUnderTest.to(Model1.class);
		validate(output);
	}

	@Test
	public void toTest_methods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = systemUnderTest.to(Model2.class);
		validate(output);
	}

	@Test
	public void toTest_parameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = systemUnderTest.to(Model3.class);
		validate(output);
	}

	@Test
	public void fillInTest_fields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var model = new Model1();
		systemUnderTest.fillIn(model);
		validate(model);
	}

	@Test
	public void fillInTest_methods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var model = new Model2();
		systemUnderTest.fillIn(model);
		validate(model);
	}

	@Test
	public void toTest_missingFields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = assertThrows(PropertyResolverException.class, () -> systemUnderTest.to(Model4.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_missingMethods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = assertThrows(PropertyResolverException.class, () -> systemUnderTest.to(Model5.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_missingParameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = assertThrows(PropertyResolverException.class, () -> systemUnderTest.to(Model6.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_optionalFields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = systemUnderTest.to(Model7.class);
		assertAll(() -> assertThat(output.getFoundString(), is("hello world")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTest_optionalMethods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = systemUnderTest.to(Model8.class);
		assertAll(() -> assertThat(output.getFoundString(), is("hello world")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTest_optionalParameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customInspector).build();
		var output = systemUnderTest.to(Model9.class);
		assertAll(() -> assertThat(output.getFoundString(), is("hello world")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	private void validate(Model1 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("hello world")),
				() -> assertThat(model.getFoundInteger(), is(32)),
				() -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
				() -> assertThat(model.getFoundLong(), is(64L)),
				() -> assertThat(model.getFoundLongPrimitive(), is(64L)),
				() -> assertThat(model.getFoundDouble(), is(2.2)),
				() -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
				() -> assertThat(model.getFoundFloat(), is(1.1F)),
				() -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
				() -> assertThat(model.getFoundBoolean(), is(true)),
				() -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
	}

	private void validate(Model2 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("hello world")),
				() -> assertThat(model.getFoundInteger(), is(32)),
				() -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
				() -> assertThat(model.getFoundLong(), is(64L)),
				() -> assertThat(model.getFoundLongPrimitive(), is(64L)),
				() -> assertThat(model.getFoundDouble(), is(2.2)),
				() -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
				() -> assertThat(model.getFoundFloat(), is(1.1F)),
				() -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
				() -> assertThat(model.getFoundBoolean(), is(true)),
				() -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
	}

	private void validate(Model3 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("hello world")),
				() -> assertThat(model.getFoundInteger(), is(32)),
				() -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
				() -> assertThat(model.getFoundLong(), is(64L)),
				() -> assertThat(model.getFoundLongPrimitive(), is(64L)),
				() -> assertThat(model.getFoundDouble(), is(2.2)),
				() -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
				() -> assertThat(model.getFoundFloat(), is(1.1F)),
				() -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
				() -> assertThat(model.getFoundBoolean(), is(true)),
				() -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
	}
}
