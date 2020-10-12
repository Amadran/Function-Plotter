package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Function func = helperAddFunction(funcName, Function.TYPE_EXP, -3.0, 3.0, constants);

        //check
        assertEquals(1, workspace.getFunctionListLength());
        assertEquals(func, workspace.getFunction(funcName));
    }

    @Test
    public void testAddFunctionMultiple() {
        //function 1
        double[] constants1 = {2.0, -1.0, -0.5};
        String funcName1 = "test func1";
        Function func1 = helperAddFunction(funcName1, Function.TYPE_EXP, -3.0, 3.0, constants1);

        //function 2
        double[] constants2 = {-1.5, 2.5};
        String funcName2 = "test func2";
        Function func2 = helperAddFunction(funcName2, Function.TYPE_LINEAR, -3.0, 3.0, constants2);

        //function 3
        double[] constants3 = {-1.5, 2.5, -0.5, -2.0, 1.0, -2.5, 0.5};
        String funcName3 = "test func3";
        Function func3 = helperAddFunction(funcName3, Function.TYPE_TRIG, -3.0, 3.0, constants3);

        //check that workspace contains all 3 functions
        assertEquals(3, workspace.getFunctionListLength());
        assertEquals(func1, workspace.getFunction(funcName1));
        assertEquals(func2, workspace.getFunction(funcName2));
        assertEquals(func3, workspace.getFunction(funcName3));
    }

    @Test
    public void testRemoveFunctionOne() {
        //repeat adding function in testAddFunctionOne
        double[] constants = {2.0, -1.5, 0.5};
        String funcName = "test func";
        Function func = helperAddFunction(funcName, Function.TYPE_EXP, -3.0, 3.0, constants);
        //        ^ not used, but keeping it for consistency with multiple function test

        //remove function
        workspace.removeFunction(funcName);
        assertEquals(0, workspace.getFunctionListLength());
        assertNull(workspace.getFunction(funcName));
    }

    @Test
    public void testRemoveFunctionMultiple() {
        //repeat adding functions in testAddFunctionMultiple
        //function 1
        double[] constants1 = {2.0, -1.0, -0.5};
        String funcName1 = "test func1";
        Function func1 = helperAddFunction(funcName1, Function.TYPE_EXP, -3.0, 3.0, constants1);

        //function 2
        double[] constants2 = {-1.5, 2.5};
        String funcName2 = "test func2";
        Function func2 = helperAddFunction(funcName2, Function.TYPE_LINEAR, -3.0, 3.0, constants2);

        //function 3
        double[] constants3 = {-1.5, 2.5, -0.5, -2.0, 1.0, -2.5, 0.5};
        String funcName3 = "test func3";
        Function func3 = helperAddFunction(funcName3, Function.TYPE_TRIG, -3.0, 3.0, constants3);

        //remove function
        workspace.removeFunction(funcName2);
        assertEquals(2, workspace.getFunctionListLength());
        assertEquals(func1, workspace.getFunction(funcName1));
        assertNull(workspace.getFunction(funcName2));
        assertEquals(func3, workspace.getFunction(funcName3));
    }

    //~~~~~~~~~~~~~HELPERS~~~~~~~~~~~~~

    //constants helper
    public void helperInitConstants(double[] constArray, HashMap<String, Double> funcConstants) {
        for (int i = 0; i < constArray.length; i++) {
            funcConstants.put(Function.CONSTANT_NAMES[i], constArray[i]);
        }
    }

    //addFunction helper
    public Function helperAddFunction(String name, String type, double left, double right, double[] constArray) {
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
}
