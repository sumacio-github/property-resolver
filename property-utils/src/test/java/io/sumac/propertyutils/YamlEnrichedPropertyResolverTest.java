package io.sumac.propertyutils;

import java.io.IOException;

import io.sumac.propertyresolver.TypedProperties;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;

public class YamlEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new TypedProperties();
        PropertiesHelper.loadFromClasspath(systemUnderTest, "test.yaml");
    }

}
