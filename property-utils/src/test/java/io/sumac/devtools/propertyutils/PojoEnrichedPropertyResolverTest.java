package io.sumac.devtools.propertyutils;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.sumac.devtools.propertyresolver.TypedProperties;
import io.sumac.devtools.propertyutils.PropertiesHelper;
import io.sumac.devtools.propertyutils.domain.Fields;
import io.sumac.devtools.propertyutils.domain.Model;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class PojoEnrichedPropertyResolverTest extends AbstractEnrichedPropertyResolverTest {

    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException, ParseException {
        systemUnderTest = new TypedProperties();
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("1984-08-17T21:42:27.639-05:00");
        Fields fields = new Fields();
        fields.setStringVal("hello world");
        fields.setBooleanVal(true);
        fields.setIntVal(32);
        fields.setLongVal(64L);
        fields.setDoubleVal(2.2);
        fields.setFloatVal(1.1F);
        fields.setDateVal(date);
        fields.setStrings(Arrays.asList("hello", "goodbye"));
        fields.setSoloString(Collections.singletonList("solo"));
        Model model = new Model();
        model.setDateVal(fields.getDateVal());
        model.setFloatVal(fields.getFloatVal());
        model.setDoubleVal(fields.getDoubleVal());
        model.setLongVal(fields.getLongVal());
        model.setIntVal(fields.getIntVal());
        model.setBooleanVal(fields.getBooleanVal());
        model.setStringVal(fields.getStringVal());
        model.setStrings(fields.getStrings());
        model.setSoloString(fields.getSoloString());
        model.setObjectVal(fields);
        model.setList(Arrays.asList(fields, fields));
        model.setInterpolated("object.string > ${object.string} and list.1.string > ${list.1.string}");
        model.setUninterpolated("object.string.notFound > ${object.string.notFound} and list.1.string.notFound > ${list.1.string.notFound}");
        PropertiesHelper.loadFromObject(systemUnderTest, model);

    }

}
