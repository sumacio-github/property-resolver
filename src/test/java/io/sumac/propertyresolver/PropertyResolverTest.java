package io.sumac.propertyresolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import io.sumac.propertyresolver.sample.Model1;
import io.sumac.propertyresolver.sample.Model2;
import io.sumac.propertyresolver.sample.Model3;

public class PropertyResolverTest {

	@Test
	public void toTest_fields() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.build();
		var output = systemUnderTest.to(Model1.class);
		validate(output);
	}

	@Test
	public void toTest_methods() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.build();
		var output = systemUnderTest.to(Model2.class);
		validate(output);
	}

	@Test
	public void toTest_parameters() {
		var systemUnderTest = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.build();
		var output = systemUnderTest.to(Model3.class);
		validate(output);
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
