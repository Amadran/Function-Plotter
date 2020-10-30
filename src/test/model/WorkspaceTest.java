package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceTest {
    private Workspace workspace;

    @BeforeEach
    public void setup() {
        workspace = new Workspace();
    }

    @Test
    public void testConstructor() {
        //check that nothing is in the workspace
        assertEquals(0, workspace.getFunctionListLength());
    }

    @Test
    public void testAddFunctionOne() {
        //init
        double[] constants = {2.0, -1.5, 0.5};
        String funcName = "test func";
        Function func = helperAddFunction(workspace, funcName, Function.TYPE_EXP, -3.0, 3.0, constants);

        //check
        assertEquals(1, workspace.getFunctionListLength());
        assertEquals(func, workspace.getFunction(funcName));
    }

    @Test
    public void testAddFunctionMultiple() {
        //create 3 functions
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //check that workspace contains all 3 functions
        assertEquals(3, workspace.getFunctionListLength());
        assertEquals(functions.get(funcNames[0]), workspace.getFunction(funcNames[0]));
        assertEquals(functions.get(funcNames[1]), workspace.getFunction(funcNames[1]));
        assertEquals(functions.get(funcNames[2]), workspace.getFunction(funcNames[2]));

        //check getFunctionList() explicitly, since it is not used anywhere
        helperGetFunctionList(functions, funcNames);
    }

    @Test
    public void testRemoveFunctionOne() {
        //repeat adding function in testAddFunctionOne
        double[] constants = {2.0, -1.5, 0.5};
        String funcName = "test func";
        Function func = helperAddFunction(workspace, funcName, Function.TYPE_EXP, -3.0, 3.0, constants);
        //        ^ not used, but keeping it for consistency with multiple function test

        //remove function
        workspace.removeFunction(funcName);
        assertEquals(0, workspace.getFunctionListLength());
        assertNull(workspace.getFunction(funcName));
    }

    @Test
    public void testRemoveFunctionMultiple() {
        //create 3 functions
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //remove function
        workspace.removeFunction(funcNames[1]);
        assertEquals(2, workspace.getFunctionListLength());
        assertEquals(functions.get(funcNames[0]), workspace.getFunction(funcNames[0]));
        assertNull(workspace.getFunction(funcNames[1]));
        assertEquals(functions.get(funcNames[2]), workspace.getFunction(funcNames[2]));
    }

    @Test
    public void testToJson() {
        //initialize 3 functions
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //initialize expected JSONObject and JSONArray
        JSONObject expectedJson = new JSONObject();
        JSONArray expectedJsonFuncArray = new JSONArray();
        helperInitExpectedJson(expectedJson, expectedJsonFuncArray, functions, funcNames);

        //check
        JSONObject actualJson = workspace.toJson();
        JSONArray actualArray = actualJson.getJSONArray("functionList");

        for (int i = 0; i < actualArray.length(); i++) {
            JsonTest.helperToJsonCheck(expectedJsonFuncArray.getJSONObject(i), actualArray.getJSONObject(i));
        }
    }

    @Test
    public void testEqualsSameObject() {
        //initialize 3 functions
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //check
        assertTrue(workspace.equals(workspace));
    }

    @Test
    public void testEqualsNull() {
        //initialize 3 functions
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //check
        assertFalse(workspace.equals(null));
    }

    @Test
    public void testEqualsDifferentObject() {
        //initialize 3 functions
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        Object object = new Object();

        //check
        assertFalse(workspace.equals(object));
    }

    @Test
    public void testEqualsEquivalentObjects() {
        //initialize 3 functions for this
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //initialize 3 functions for otherWorkspace
        HashMap<String, Function> otherFunctions = new HashMap<>();
        String[] otherFuncNames = {"func1", "func2", "func3"};
        Workspace otherWorkspace = new Workspace();
        helperInitMultipleFunctions(otherWorkspace, otherFunctions, otherFuncNames); //functions are defined inside this method

        assertTrue(workspace.equals(otherWorkspace));
    }

    @Test
    public void testHashCodeEqualObjects() {
        //initialize 3 functions for this
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //initialize 3 functions for otherWorkspace
        HashMap<String, Function> otherFunctions = new HashMap<>();
        String[] otherFuncNames = {"func1", "func2", "func3"};
        Workspace otherWorkspace = new Workspace();
        helperInitMultipleFunctions(otherWorkspace, otherFunctions, otherFuncNames); //functions are defined inside this method

        //check
        assertEquals(otherWorkspace.hashCode(), workspace.hashCode());
    }

    @Test
    public void testHashCodeNotEqualObjects() {
        //initialize 3 functions for this
        HashMap<String, Function> functions = new HashMap<>();
        String[] funcNames = {"func1", "func2", "func3"};
        helperInitMultipleFunctions(workspace, functions, funcNames); //functions are defined inside this method

        //initialize 3 functions for otherWorkspace
        HashMap<String, Function> otherFunctions = new HashMap<>();
        String[] otherFuncNames = {"func1", "func2", "func3"};
        Workspace otherWorkspace = new Workspace();
        helperInitMultipleOtherFunctions(otherWorkspace, otherFunctions, otherFuncNames); //functions are defined inside this method

        //check
        assertNotEquals(otherWorkspace.hashCode(), workspace.hashCode());
    }

    //~~~~~~~~~~~~~HELPERS~~~~~~~~~~~~~

    //constants helper
    private void helperInitConstants(double[] constArray, HashMap<String, Double> funcConstants) {
        for (int i = 0; i < constArray.length; i++) {
            funcConstants.put(Function.CONSTANT_NAMES[i], constArray[i]);
        }
    }

    //addFunction helper
    private Function helperAddFunction(Workspace workspace, String name, String type, double left, double right, double[] constArray) {
        //initialize constants and domain of function to add
        HashMap<String, Double> constants = new HashMap<>();
        List<Double> domain = new ArrayList<>();
        domain.add(left);
        domain.add(right);
        helperInitConstants(constArray, constants);

        //create Function object, add it to workspace with a name
        Function func = new Function(type, constants, domain);
        workspace.addFunction(func, name);

        return func; //in order to use it in test method
    }

    //helper to test getFunctionList() inside testAddFunctionMultiple() test (since it is not tested implicitly)
    private void helperGetFunctionList(HashMap<String, Function> functions, String[] funcNames) {
        //initialize expected values
        HashMap<String, Function> expectedFunctions = new HashMap<>();
        expectedFunctions.put(funcNames[0], functions.get(funcNames[0]));
        expectedFunctions.put(funcNames[1], functions.get(funcNames[1]));
        expectedFunctions.put(funcNames[2], functions.get(funcNames[2]));

        //return actual values to test
        HashMap<String, Function> actualFunctions = workspace.getFunctionList();
        ArrayList<String> actualFunctionNames = new ArrayList<>(actualFunctions.keySet());

        //check names and Function objects
        int i = 0;
        for (String expectedFuncName : expectedFunctions.keySet()) {
            assertEquals(expectedFuncName, actualFunctionNames.get(i));
            i++;
        }
        assertEquals(expectedFunctions, actualFunctions);
    }

    //helper to create 3 functions at once (define them inside here)
    private void helperInitMultipleFunctions(Workspace workspace, HashMap<String, Function> functions, String[] names) {
        //function 1
        double[] constants1 = {2.0, -1.0, -0.5};
        Function func1 = helperAddFunction(workspace, names[0], Function.TYPE_EXP, -3.0, 3.0, constants1);
        functions.put(names[0], func1); //"functions" list comes in empty

        //function 2
        double[] constants2 = {-1.5, 2.5};
        Function func2 = helperAddFunction(workspace, names[1], Function.TYPE_LINEAR, -3.0, 3.0, constants2);
        functions.put(names[1], func2);

        //function 3
        double[] constants3 = {-1.5, 2.5, -0.5, -2.0, 1.0, -2.5, 0.5};
        Function func3 = helperAddFunction(workspace, names[2], Function.TYPE_TRIG, -3.0, 3.0, constants3);
        functions.put(names[2], func3);
    }

    //toJson helper (initializes expectedJson JSONObject and underlying JSONArray)
    private void helperInitExpectedJson(JSONObject expectedJson, JSONArray expectedJsonFuncArray,
                                        HashMap<String, Function> functions, String[] funcNames) {
        //populate expected JSONObject (and underlying JSONArray)
        for (String funcName : funcNames) {
            JSONObject funcJson = new JSONObject();
            JsonTest.helperInitJsonObject(funcJson, funcName, functions.get(funcName).getFunctionType(),
                    functions.get(funcName).getConstants(), functions.get(funcName).getDomain(),
                    functions.get(funcName).getValuesX(), functions.get(funcName).getValuesY());
            expectedJsonFuncArray.put(funcJson);
        }
        expectedJson.put("functionList", expectedJsonFuncArray);
    }

    //helper to create 3 functions at once (define them inside here; functions are different from those in
    // helperInitMultipleFunctions() for the purpose of testing hashCode())
    private void helperInitMultipleOtherFunctions(Workspace workspace, HashMap<String, Function> functions, String[] names) {
        //function 1
        double[] constants1 = {2.5, -1.0, -0.5};
        Function func1 = helperAddFunction(workspace, names[0], Function.TYPE_EXP, -3.0, 3.0, constants1);
        functions.put(names[0], func1); //"functions" list comes in empty

        //function 2
        double[] constants2 = {-1.5, -0.5};
        Function func2 = helperAddFunction(workspace, names[1], Function.TYPE_LINEAR, -3.0, 3.0, constants2);
        functions.put(names[1], func2);

        //function 3
        double[] constants3 = {-1.5, 2.5, 0.5, -2.0, 1.0, 2.5, 0.5};
        Function func3 = helperAddFunction(workspace, names[2], Function.TYPE_TRIG, -3.0, 3.0, constants3);
        functions.put(names[2], func3);
    }
}
