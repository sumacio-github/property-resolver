package io.sumac.propertyresolver;

import java.io.IOException;

import io.sumac.propertyresolver.domain.Model;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;

public class XmlElementEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new Properties();
        PropertiesHelper.loadXmlFromClasspath(systemUnderTest, "test_1.xml", Model.class);
    }
}
