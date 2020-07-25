package io.sumac.propertyutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sumac.propertyresolver.TypedProperties;
import io.sumac.propertyutils.domain.Model;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class XmlParameterEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new TypedProperties();
        PropertiesHelper.loadXmlFromClasspath(systemUnderTest, "test_2.xml", Model.class);
    }

}
