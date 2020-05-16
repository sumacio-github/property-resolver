package io.sumac.propertyresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sumac.propertyresolver.domain.Fields;
import io.sumac.propertyresolver.domain.Model;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PojoEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException, ParseException {
        systemUnderTest = new ExtendedEnrichedProperties();
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("1984-08-17T21:42:27.639-05:00");
        Fields fields = new Fields();
        fields.setStringVal("hello world");
        fields.setBooleanVal(true);
        fields.setIntVal(32);
        fields.setLongVal(64L);
        fields.setDoubleVal(2.2);
        fields.setFloatVal(1.1F);
        fields.setDateVal(date);
        Model model = new Model();
        model.setDateVal(fields.getDateVal());
        model.setFloatVal(fields.getFloatVal());
        model.setDoubleVal(fields.getDoubleVal());
        model.setLongVal(fields.getLongVal());
        model.setIntVal(fields.getIntVal());
        model.setBooleanVal(fields.getBooleanVal());
        model.setStringVal(fields.getStringVal());
        model.setObjectVal(fields);
        model.setList(Arrays.asList(fields, fields));
        model.setStrings(Arrays.asList("hello", "goodbye"));
        systemUnderTest.loadFromObject(model);

    }

}
