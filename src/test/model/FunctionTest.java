package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {
    private String testType;
    private List<Double> testDomain;
    private HashMap<String, Double> testConst;
    private List<Double> testXVals;
    private List<Double> testYVals;
    private final int MAX_NUM_CONSTANTS = 7;

    private final String[] constantKeys = {"a","b","c","d","e","f","g"};

    @BeforeEach
    public void setup() {
        testDomain = new ArrayList<>();
        testConst = new HashMap<>();
        testXVals = new ArrayList<>();
        testYVals = new ArrayList<>();
    }

    //constants helper
    public void initConstants(double[] constants) {
        for (int i = 0; i < constants.length; i++) {
            testConst.put(constantKeys[i], constants[i]);
        }
    }

    //constructor initialization helper (also generates function values to test)
    public void helperConstructorInit(String funcType, double left, double right) {
        testType = funcType;
        testDomain.add(left);
        testDomain.add(right);

        //initialize x-values to test
        double i = left;
        while (i <= right) {
            testXVals.add(i);
            i += Function.DELTA;
        }

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
    public void helperConstructorCheck(Function f, double left, double right) {
        assertEquals(testType, f.getFunctionType());
        assertEquals(testDomain.get(0), f.getDomain().get(0), Function.DELTA / 10.0);
        assertEquals(testDomain.get(1), f.getDomain().get(1), Function.DELTA / 10.0);
        for (Map.Entry<String, Double> c : testConst.entrySet()) {
            assertEquals(c.getValue(), f.getConstants().get(c.getKey()), Function.DELTA / 10.0);
        }
        for (int i = 0; i < testXVals.size(); i++) {
            assertEquals(testXVals.get(i), f.getValuesX().get(i), Function.DELTA / 10.0);
        }
        for (int i = 0; i < testYVals.size(); i++) {
            assertEquals(testYVals.get(i), f.getValuesY().get(i), Function.DELTA / 10.0);
        }
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
        helperConstructorCheck(func, -3.0, 3.0);
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
        helperConstructorCheck(func, -3.0, 3.0);
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
        helperConstructorCheck(func, -3.0, 3.0);
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
        helperConstructorCheck(func, -3.0, 3.0);
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
        helperConstructorCheck(func, -3.0, 3.0);
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
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / 10.0);
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
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / 10.0);
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
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / 10.0);
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
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / 10.0);
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
        assertEquals(expectedY, func.evalFunction(x), Function.DELTA / 10.0);
    }
}