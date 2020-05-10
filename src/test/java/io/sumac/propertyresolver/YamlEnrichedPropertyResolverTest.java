package io.sumac.propertyresolver;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

public class YamlEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new EnrichedProperties();
        systemUnderTest.loadFromYamlString(readFromInputStream(this.getClass().getClassLoader().getResourceAsStream("test.yml")));
    }

}
