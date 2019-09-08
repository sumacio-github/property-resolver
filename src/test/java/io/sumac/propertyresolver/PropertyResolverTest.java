package io.sumac.propertyresolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import io.sumac.propertyresolver.functions.SerializableBiConsumer;
import io.sumac.propertyresolver.functions.SerializableConsumer;
import io.sumac.propertyresolver.functions.SerializableUnaryOperator;
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

	private static final Logger LOGGER = LogManager.getLogger(PropertyResolverTest.class);

	private SerializableBiConsumer customLoggingInspector = (key, value) -> {
		LOGGER.debug("{}={}", key, value);
	};

	private SerializableConsumer customLoggingPropertyNotFoundHandler = key -> {
		LOGGER.warn("{}=<NULL>", key);
	};

	private SerializableUnaryOperator customToUpperCaseTransformer = s -> s.toUpperCase();

	@Test
	public void toTest_fields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = systemUnderTest.to(Model1.class);
		validate(output);
	}

	@Test
	public void toTest_methods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = systemUnderTest.to(Model2.class);
		validate(output);
	}

	@Test
	public void toTest_parameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = systemUnderTest.to(Model3.class);
		validate(output);
	}

	@Test
	public void fillInTest_fields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var model = new Model1();
		systemUnderTest.fillIn(model);
		validate(model);
	}

	@Test
	public void fillInTest_methods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var model = new Model2();
		systemUnderTest.fillIn(model);
		validate(model);
	}

	@Test
	public void toTest_missingFields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = assertThrows(PropertyResolverException.class, () -> systemUnderTest.to(Model4.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_missingMethods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = assertThrows(PropertyResolverException.class, () -> systemUnderTest.to(Model5.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_missingParameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = assertThrows(PropertyResolverException.class, () -> systemUnderTest.to(Model6.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_optionalFields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = systemUnderTest.to(Model7.class);
		assertAll(() -> assertThat(output.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTest_optionalMethods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = systemUnderTest.to(Model8.class);
		assertAll(() -> assertThat(output.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTest_optionalParameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		var output = systemUnderTest.to(Model9.class);
		assertAll(() -> assertThat(output.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void serializableTest() throws FileNotFoundException, IOException, ClassNotFoundException {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("property_resolver.bin"))) {
			out.writeObject(systemUnderTest);
		}
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("property_resolver.bin"))) {
			PropertyResolver output = (PropertyResolver) in.readObject();
			validate(output.to(Model1.class));
		}
	}

	private void validate(Model1 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("HELLO WORLD")),
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
		assertAll(() -> assertThat(model.getFoundString(), is("HELLO WORLD")),
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
		assertAll(() -> assertThat(model.getFoundString(), is("HELLO WORLD")),
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
