package io.sumac.devtools.propertyutils;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.sumac.devtools.propertyresolver.TypedProperties;
import io.sumac.devtools.propertyutils.PropertiesHelper;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class DefaultEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new TypedProperties();
        PropertiesHelper.loadFromClasspath(systemUnderTest, "test.txt");
    }
}
