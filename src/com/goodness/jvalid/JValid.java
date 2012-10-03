package com.goodness.jvalid;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.main.JsonSchema;
import org.eel.kitchen.jsonschema.main.JsonSchemaException;
import org.eel.kitchen.jsonschema.main.JsonSchemaFactory;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.eel.kitchen.jsonschema.util.JsonLoader;

import java.io.File;
import java.io.IOException;

/**
 * User: Oleg Soroka
 * Date: 18.09.12
 * Time: 21:22
 */
public class JValid {
    public static void main(String[] args) throws JsonSchemaException, IOException {
        validate(args);
    }

    public static ValidationReport validate(String[] args) {
        String message1 = "Hi! Thanks to use jvalid. To use this validator specify schema file as first parameter, \n" +
                "and data file as second, e.g \"java -jar jvalid.jar mySchema.json myData.json\"\n" +
                "If you not specify parameters it fill try to find \"schema.json\" and \"data.json\" in current folder.";

        String message2 = "Default files (\"schema.json\" and/or \"data.json\") does not exist.";

        String schemaFilePath = "src/com/goodness/jvalid/tests/schemas/schema.json";
        String dataFilePath = "src/com/goodness/jvalid/tests/schemas/data.json";

        File schemaFile = new File(schemaFilePath);
        File dataFile = new File(dataFilePath);

        String namespace = schemaFile.getAbsoluteFile().getParentFile().getAbsolutePath();

        if (args.length != 2) {
            System.out.println(message1);
        } else {
            schemaFilePath = args[0];
            dataFilePath = args[1];
            schemaFile = new File(schemaFilePath);
            dataFile = new File(dataFilePath);
        }

        System.out.println(String.format("Namespace:        %s", namespace));
        System.out.println(String.format("File 1 (schema):  %s", schemaFile.getAbsolutePath()));
        System.out.println(String.format("File 2 (data):    %s", dataFile.getAbsolutePath()));

        ValidationReport report = null;

        if (schemaFile.exists() && dataFile.exists()) {
            String root = JValid.class.getProtectionDomain().getCodeSource().getLocation().toString();

            try {
                JsonNode schemaNode = JsonLoader.fromFile(schemaFile);
                JsonNode testData = JsonLoader.fromFile(dataFile);

                JsonSchemaFactory factory = new JsonSchemaFactory.Builder()
                        .setNamespace(String.format("file:%s/", namespace)).build();

                JsonSchema schema = factory.fromSchema(schemaNode);
                report = schema.validate(testData);

                System.out.println("=================== Report ===================");
                System.out.println(report);
                for (String s : report.getMessages()) {
                    System.out.println(s);
                }
                System.out.println("==============================================");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(message2);
        }

        return report;
    }
}