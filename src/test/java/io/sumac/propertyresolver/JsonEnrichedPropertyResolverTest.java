package io.sumac.propertyresolver;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

public class JsonEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new EnrichedProperties();
        systemUnderTest.loadFromJsonString(readFromInputStream(this.getClass().getClassLoader().getResourceAsStream("test.json")));
    }
}
