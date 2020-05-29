package io.sumac.propertyresolver;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;

public class YamlEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new Properties();
        PropertiesHelper.loadFromClasspath(systemUnderTest, "test.yaml");
    }

}
