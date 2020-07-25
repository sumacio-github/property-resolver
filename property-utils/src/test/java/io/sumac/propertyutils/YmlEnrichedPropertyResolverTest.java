package io.sumac.propertyutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sumac.propertyresolver.TypedProperties;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class YmlEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new TypedProperties();
        PropertiesHelper.loadFromClasspath(systemUnderTest, "test.yml");
    }

}
