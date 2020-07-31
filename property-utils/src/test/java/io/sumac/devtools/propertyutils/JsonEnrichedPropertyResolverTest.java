package io.sumac.devtools.propertyutils;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.sumac.devtools.propertyresolver.TypedProperties;
import io.sumac.devtools.propertyutils.PropertiesHelper;

public class JsonEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new TypedProperties();
        PropertiesHelper.loadFromClasspath(systemUnderTest, "test.json");
    }
}
