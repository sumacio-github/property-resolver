package io.sumac.propertyhelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class DefaultEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new Properties();
        PropertiesHelper.loadFromClasspath(systemUnderTest, "test.txt");
    }
}