package io.sumac.propertyresolver.utility;

import io.sumac.propertyresolver.utility.PreCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PreConditionTest {
    @Test
    public void testParameterNotNull() {
        String input = "test";
        PreCondition.Parameter.notNull(input);
    }

    @Test
    public void testParameterNotNull_isNull() {
        String input = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> PreCondition.Parameter.notNull(input));
    }

    @Test
    public void testResultNotNull() {
        String input = "test";
        assertThat(PreCondition.Result.notNull(input), is("test"));
    }

    @Test
    public void testResultNotNull_isNull() {
        String input = null;
        Assertions.assertThrows(IllegalStateException.class, () -> PreCondition.Result.notNull(input));
    }

}
