package io.sumac.propertyresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sumac.propertyresolver.domain.Model;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class XmlParameterEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new EnrichedProperties();
        systemUnderTest.loadFromXmlString(readFromInputStream(this.getClass().getClassLoader().getResourceAsStream("test_2.xml")), Model.class);
    }

}
