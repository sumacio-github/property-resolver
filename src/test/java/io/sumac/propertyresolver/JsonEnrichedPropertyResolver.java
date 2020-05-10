package io.sumac.propertyresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JsonEnrichedPropertyResolver extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException {
        systemUnderTest = new EnrichedProperties();
        systemUnderTest.loadFromJsonString(readFromInputStream(this.getClass().getClassLoader().getResourceAsStream("test.json")));
    }
    @Test
    public void testGetString_exists() {
        super.testGetString_exists();
    }

    @Test
    public void testGetString_notExists() {
        super.testGetString_notExists();
    }

    @Test
    public void testGetStringRequired_exists() {
        super.testGetStringRequired_exists();
    }

    @Test
    public void testGetStringRequired_notExists() {
        super.testGetStringRequired_notExists();
    }

    @Test
    public void testGetBoolean_exists() {
        super.testGetBoolean_exists();
    }

    @Test
    public void testGetBoolean_notExists() {
        super.testGetBoolean_notExists();
    }

    @Test
    public void testGetBooleanRequired_exists() {
        super.testGetBooleanRequired_exists();
    }

    @Test
    public void testGetBooleanRequired_notExists() {
        super.testGetBooleanRequired_notExists();
    }

    @Test
    public void testGetInt_exists() {
        super.testGetInt_exists();
    }

    @Test
    public void testGetInt_notExists() {
        super.testGetInt_notExists();
    }

    @Test
    public void testGetIntRequired_exists() {
        super.testGetIntRequired_exists();
    }

    @Test
    public void testGetIntRequired_notExists() {
        super.testGetIntRequired_notExists();
    }

    @Test
    public void testGetLong_exists() {
        super.testGetLong_exists();
    }

    @Test
    public void testGetLong_notExists() {
        super.testGetLong_notExists();
    }

    @Test
    public void testGetLongRequired_exists() {
        super.testGetLongRequired_exists();
    }

    @Test
    public void testGetLongRequired_notExists() {
        super.testGetLongRequired_notExists();
    }

    @Test
    public void testGetDouble_exists() {
        super.testGetDouble_exists();
    }

    @Test
    public void testGetDouble_notExists() {
        super.testGetDouble_notExists();
    }

    @Test
    public void testGetDoubleRequired_exists() {
        super.testGetDoubleRequired_exists();
    }

    @Test
    public void testGetDoubleRequired_notExists() {
        super.testGetDoubleRequired_notExists();
    }

    @Test
    public void testGetFloat_exists() {
        super.testGetFloat_exists();
    }

    @Test
    public void testGetFloat_notExists() {
        super.testGetFloat_notExists();
    }

    @Test
    public void testGetFloatRequired_exists() {
        super.testGetFloatRequired_exists();
    }

    @Test
    public void testGetFloatRequired_notExists() {
        super.testGetFloatRequired_notExists();
    }

    @Test
    public void testGetDate_exists() {
        super.testGetDate_exists();
    }

    @Test
    public void testGetDate_notExists() {
        super.testGetDate_notExists();
    }

    @Test
    public void testGetDateRequired_exists() {
        super.testGetDateRequired_exists();
    }

    @Test
    public void testGetDateRequired_notExists() {
        super.testGetDateRequired_notExists();
    }
}
