package io.sumac.propertyresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sumac.propertyresolver.domain.Model;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class XmlParameterEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new Properties();
        PropertiesHelper.loadXmlFromClasspath(systemUnderTest, "test_2.xml", Model.class);
    }

}
