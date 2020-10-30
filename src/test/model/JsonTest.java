package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//contains toJson testing code common to FunctionTest and WorkspaceTest
public class JsonTest {

    //helper method to initialize a JSONObject representing a Function object
    public static void helperInitJsonObject(JSONObject json, String name, String type,
                                            HashMap<String, Double> constants, List<Double> domain,
                                            List<Double> valuesX, List<Double> valuesY) {
        json.put("name", name);
        json.put("type", type);
        json.put("constants", constants);
        json.put("domain", domain);
        json.put("valuesX", valuesX);
        json.put("valuesY", valuesY);
    }

    //helper method to check Function toJson's returned JSONObject
    public static void helperToJsonCheck(JSONObject expectedJson, JSONObject actualJson) {
        //check name, type, and domain
        assertEquals(expectedJson.get("name"), actualJson.get("name"));
        assertEquals(expectedJson.get("type"), actualJson.get("type"));
        assertEquals(expectedJson.getJSONArray("domain").get(0), actualJson.getJSONArray("domain").get(0));
        assertEquals(expectedJson.getJSONArray("domain").get(1), actualJson.getJSONArray("domain").get(1));

        //check constants
        JSONObject expectedConst = expectedJson.getJSONObject("constants");
        JSONObject actualConst = actualJson.getJSONObject("constants");
        for (String constKey : expectedConst.keySet()) {
            assertEquals(expectedConst.get(constKey), actualConst.get(constKey));
        }

        //check x values
        JSONArray expectedValuesX = expectedJson.getJSONArray("valuesX");
        JSONArray actualValuesX = actualJson.getJSONArray("valuesX");
        for (int i = 0; i < expectedValuesX.length(); i++) {
            assertEquals(expectedValuesX.getDouble(i), actualValuesX.getDouble(i), Function.DELTA / 100.0);
        }

        //check y values
        JSONArray expectedValuesY = expectedJson.getJSONArray("valuesY");
        JSONArray actualValuesY = actualJson.getJSONArray("valuesY");
        for (int i = 0; i < expectedValuesY.length(); i++) {
            assertEquals(expectedValuesY.getDouble(i), actualValuesY.getDouble(i), Function.DELTA / 100.0);
        }
    }
}
