package com.goodness.jvalid.tests;

import com.goodness.jvalid.JValid;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Oleg Soroka
 * Date: 03.10.12
 * Time: 13:50
 */
public class MainTest {

    String schema = "src/com/goodness/jvalid/tests/schemas/schema.json";
    String data = "src/com/goodness/jvalid/tests/schemas/data.json";
    String data_incorrect = "src/com/goodness/jvalid/tests/schemas/data_incorrect.json";

    @Test
    public void testValidate() throws Exception {
        ValidationReport report = JValid.validate(new String[]{schema, data});
        assertEquals(0, report.getMessages().size());
    }

    @Test
    public void testValidateIncorrect() throws Exception {
        ValidationReport report = JValid.validate(new String[]{schema, data_incorrect});
        assertEquals(1, report.getMessages().size());
    }
}