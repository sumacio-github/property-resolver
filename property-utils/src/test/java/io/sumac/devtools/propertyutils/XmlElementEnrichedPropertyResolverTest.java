package io.sumac.devtools.propertyutils;

import java.io.IOException;

import io.sumac.devtools.propertyresolver.TypedProperties;
import io.sumac.devtools.propertyutils.PropertiesHelper;
import io.sumac.devtools.propertyutils.domain.Model;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;

public class XmlElementEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new TypedProperties();
        PropertiesHelper.loadXmlFromClasspath(systemUnderTest, "test_1.xml", Model.class);
    }
}
