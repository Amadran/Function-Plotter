package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.lang.Double.NaN;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {
    private String testType;
    private List<Double> testDomain;
    private HashMap<String, Double> testConst;
    private List<Double> testXVals;
    private List<Double> testYVals;

    private final String[] constantKeys = {"a","b","c","d","e","f","g"};
    private final double ASSERT_EQUALS_TOLERANCE = 1e6;

    @BeforeEach
    public void setup() {
        testDomain = new ArrayList<>();
        testConst = new HashMap<>();
        testXVals = new ArrayList<>();
        testYVals = new ArrayList<>();
    }

    @Test
    public void testConstructorLinear() {
        //initialization
        double[] consts = {2.0, 1.5};
        initConstants(consts);
        helperConstructorInit(Function.TYPE_LINEAR, -3.0, 3.0);
        
        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func);
    }

    @Test
    public void testConstructorPolynomial() {
        //initialization
        double[] consts = {-2.0, -2.0, 3.0, 2.5, -1.0, 0.5};
        initConstants(consts);
        helperConstructorInit(Function.TYPE_POLY, -3.0, 3.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func);
    }

    @Test
    public void testConstructorExponential() {
        //initialization
        double[] consts = {2.0, 2.0, 3.0};
        initConstants(consts);
        helperConstructorInit(Function.TYPE_EXP, -3.0, 3.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func);
    }

    @Test
    public void testConstructorTrigonometric() {
        //initialization
        double[] consts = {2.0, 2.0, -1.5, -1.5, 2.0, 2.0, 0.0};
        initConstants(consts);
        helperConstructorInit(Function.TYPE_TRIG, -3.0, 3.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func);
    }

    @Test
    public void testConstructorLogarithmic() {
        //initialization
        double[] consts = {2.0, -2.0, 1.5};
        initConstants(consts);
        helperConstructorInit(Function.TYPE_LOG, -3.0, 3.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func);
    }

    @Test
    public void testEvalFunctionLinear() {
        testType = Function.TYPE_LINEAR;
        testDomain.add(-3.0);
        testDomain.add(3.0);
        double[] consts = {1.5, -0.5};
        initConstants(consts);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * x + testConst.get("b");
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
    }

    @Test
    public void testEvalFunctionPolynomial() {
        testType = Function.TYPE_POLY;
        testDomain.add(-3.0);
        testDomain.add(3.0);
        double[] consts = {1.0, -2.0, 1.0, 1.0, -1.5, 0.5};
        initConstants(consts);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * x*x*x*x*x + testConst.get("b") * x*x*x*x
                         + testConst.get("c") * x*x*x + testConst.get("d") * x*x
                         + testConst.get("e") * x + testConst.get("f");
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
    }

    @Test
    public void testEvalFunctionExponential() {
        testType = Function.TYPE_EXP;
        testDomain.add(-3.0);
        testDomain.add(3.0);
        double[] consts = {0.5, -2.5, -0.5};
        initConstants(consts);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * exp(testConst.get("b") * x) + testConst.get("c");
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
    }

    @Test
    public void testEvalFunctionTrigonometric() {
        testType = Function.TYPE_TRIG;
        testDomain.add(-3.0);
        testDomain.add(3.0);
        double[] consts = {0.5, 1.5, -0.5, 2.0, 0.5, -2.5, 0.0};
        initConstants(consts);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * sin(testConst.get("b") * x)
                         + testConst.get("c") * cos(testConst.get("d") * x)
                         + testConst.get("e") * tan(testConst.get("f") * x) + testConst.get("g");
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
    }

    @Test
    public void testEvalFunctionLogarithmic() {
        testType = Function.TYPE_LOG;
        testDomain.add(-3.0);
        testDomain.add(3.0);
        double[] consts = {1.5, 3.0, -0.5};
        initConstants(consts);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * log(testConst.get("b") * x) + testConst.get("c");
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
    }

    @Test
    public void testEvalFunctionNAN() {
        testType = "not a function type";
        testDomain.add(-3.0);
        testDomain.add(3.0);
        double[] consts = {1.5, 3.0, -0.5};
        initConstants(consts);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = NaN;
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
    }

    @Test
    public void testToJson() {
        //initialize Function object
        double[] consts = {1.5, -1.5, 0.0, -0.5, 2.5, 1.0};
        helperTestToJson("testFunction", consts, -2.4, 2.4);
    }

    //~~~~~~~~~~~~~~~~HELPERS~~~~~~~~~~~~~~~~~

    //constants helper
    private void initConstants(double[] constants) {
        for (int i = 0; i < constants.length; i++) {
            testConst.put(constantKeys[i], constants[i]);
        }
    }

    //constructor initialization helper (also generates function values to test)
    private void helperConstructorInit(String funcType, double left, double right) {
        helperConstructorInitAllButY(funcType, left, right);

        //initialize y-values to test
        switch (funcType) {
            case Function.TYPE_LINEAR:
                for (Double x : testXVals) {
                    double y = testConst.get("a") * x + testConst.get("b");
                    testYVals.add(y);
                }
                break;
            case Function.TYPE_POLY:
                for (Double x : testXVals) {
                    double y = testConst.get("a") * x*x*x*x*x + testConst.get("b") * x*x*x*x
                            + testConst.get("c") * x*x*x + testConst.get("d") * x*x
                            + testConst.get("e") * x + testConst.get("f");
                    testYVals.add(y);
                }
                break;
            default:
                helperConstructorInitSwitchSplit(funcType);
        }
    }

    //helper for helperConstructorInit (does all initialization except for testYVals)
    private void helperConstructorInitAllButY(String funcType, double left, double right) {
        testType = funcType;
        testDomain.add(left);
        testDomain.add(right);

        //initialize x-values to test
        double i = left;
        while (i <= right) {
            testXVals.add(i);
            i += Function.DELTA;
        }
    }

    //helper for helperConstructorInit (splits large switch statement for testYVals initialization)
    private void helperConstructorInitSwitchSplit(String funcType) {
        switch (funcType) {
            case Function.TYPE_EXP:
                for (Double x : testXVals) {
                    double y = testConst.get("a") * exp(testConst.get("b") * x) + testConst.get("c");
                    testYVals.add(y);
                }
                break;
            case Function.TYPE_TRIG:
                for (Double x : testXVals) {
                    double y = testConst.get("a") * sin(testConst.get("b") * x)
                            + testConst.get("c") * cos(testConst.get("d") * x)
                            + testConst.get("e") * tan(testConst.get("f") * x) + testConst.get("g");
                    testYVals.add(y);
                }
                break;
            case Function.TYPE_LOG:
                for (Double x : testXVals) {
                    double y = testConst.get("a") * log(testConst.get("b") * x) + testConst.get("c");
                    testYVals.add(y);
                }
        }
    }

    //constructor checking helper
    private void helperConstructorCheck(Function f) {
        assertEquals(testType, f.getFunctionType());
        assertEquals(testDomain.get(0), f.getDomain().get(0), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
        assertEquals(testDomain.get(1), f.getDomain().get(1), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
        for (Map.Entry<String, Double> c : testConst.entrySet()) {
            assertEquals(c.getValue(), f.getConstants().get(c.getKey()), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
        }
        for (int i = 0; i < testXVals.size(); i++) {
            assertEquals(testXVals.get(i), f.getValuesX().get(i), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
        }
        for (int i = 0; i < testYVals.size(); i++) {
            assertEquals(testYVals.get(i), f.getValuesY().get(i), Function.DELTA / ASSERT_EQUALS_TOLERANCE);
        }
    }

    //testToJson() helper
    private void helperTestToJson(String name, double[] consts, double left, double right) {
        //initialize Function object
        initConstants(consts);
        helperConstructorInit(Function.TYPE_POLY, left, right);
        Function func = new Function(testType, testConst, testDomain);

        //initialize test JSONObject
        JSONObject expectedJson = new JSONObject();
        JsonTest.helperInitJsonObject(expectedJson, name, testType, testConst, testDomain, testXVals, testYVals);

        //check the JSONObject
        JSONObject actualJson = func.toJson(name);
        JsonTest.helperToJsonCheck(expectedJson, actualJson);
    }
}