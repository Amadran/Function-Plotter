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

    @BeforeEach
    public void setup() {
        testDomain = new ArrayList<>();
        testConst = new HashMap<>();
        testXVals = new ArrayList<>();
        testYVals = new ArrayList<>();
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
            case "linear":
                for (Double x : testXVals) {
                    double y = testConst.get("a") * x + testConst.get("b");
                    testYVals.add(y);
                }
                break;
            case "polynomial":
                for (Double x : testXVals) {
                    double y = testConst.get("a") * x*x*x*x*x + testConst.get("b") * x*x*x*x
                             + testConst.get("c") * x*x*x + testConst.get("d") * x*x
                             + testConst.get("e") * x + testConst.get("f");
                    testYVals.add(y);
                }
                break;
            case "exponential":
                for (Double x : testXVals) {
                    double y = testConst.get("a") * exp(testConst.get("b") * x) + testConst.get("c");
                    testYVals.add(y);
                }
                break;
            case "trigonometric":
                for (Double x : testXVals) {
                    double y = testConst.get("a") * sin(testConst.get("b") * x)
                             + testConst.get("c") * cos(testConst.get("d") * x)
                             + testConst.get("e") * tan(testConst.get("f") * x) + testConst.get("g");
                    testYVals.add(y);
                }
                break;
            case "logarithmic":
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
        helperConstructorInit("linear", -3.0, 3.0);
        testConst.put("a", 2.0);
        testConst.put("b", 1.5);
        
        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func, -3.0, 3.0);
    }

    @Test
    public void testConstructorPolynomial() {
        //initialization
        helperConstructorInit("polynomial", -3.0, 3.0);
        testConst.put("a", -2.0);
        testConst.put("b", -2.0);
        testConst.put("c", 3.0);
        testConst.put("d", 2.5);
        testConst.put("e", -1.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func, -3.0, 3.0);
    }

    @Test
    public void testConstructorExponential() {
        //initialization
        helperConstructorInit("exponential", -3.0, 3.0);
        testConst.put("a", 2.0);
        testConst.put("b", 2.0);
        testConst.put("c", 3.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func, -3.0, 3.0);
    }

    @Test
    public void testConstructorTrigonometric() {
        //initialization
        helperConstructorInit("trigonometric", -3.0, 3.0);
        testConst.put("a", 2.0);
        testConst.put("b", 2.0);
        testConst.put("c", -1.5);
        testConst.put("d", -1.5);
        testConst.put("e", 2.0);
        testConst.put("f", 2.0);
        testConst.put("g", 0.0);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func, -3.0, 3.0);
    }

    @Test
    public void testConstructorLogarithmic() {
        //initialization
        helperConstructorInit("logarithmic", -3.0, 3.0);
        testConst.put("a", 2.0);
        testConst.put("b", -2.0);
        testConst.put("c", 1.5);

        //test
        Function func = new Function(testType, testConst, testDomain);

        //check
        helperConstructorCheck(func, -3.0, 3.0);
    }

    //testEvalAtX initialization helper
    public void helperTestEvalAtXInit() {

    }

    @Test
    public void testEvalAtXLinear() {
        testType = "linear";
        testDomain.add(-3.0);
        testDomain.add(3.0);
        testConst.put("a", 1.5);
        testConst.put("b", -0.5);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * x + testConst.get("b");
        assertEquals(expectedY, func.evalAtX(x), Function.DELTA / 10.0);
    }

    @Test
    public void testEvalAtXPolynomial() {
        testType = "polynomial";
        testDomain.add(-3.0);
        testDomain.add(3.0);
        testConst.put("a", 1.0);
        testConst.put("b", -2.0);
        testConst.put("c", 1.0);
        testConst.put("d", 1.0);
        testConst.put("e", -1.5);
        testConst.put("f", 0.5);


        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * x*x*x*x*x + testConst.get("b") * x*x*x*x
                         + testConst.get("c") * x*x*x + testConst.get("d") * x*x
                         + testConst.get("e") * x + testConst.get("f");
        assertEquals(expectedY, func.evalAtX(x), Function.DELTA / 10.0);
    }

    @Test
    public void testEvalAtXExponential() {
        testType = "exponential";
        testDomain.add(-3.0);
        testDomain.add(3.0);
        testConst.put("a", 0.5);
        testConst.put("b", -2.5);
        testConst.put("c", -0.5);

        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * exp(testConst.get("b") * x) + testConst.get("c");
        assertEquals(expectedY, func.evalAtX(x), Function.DELTA / 10.0);
    }

    @Test
    public void testEvalAtXTrigonometric() {
        testType = "trigonometric";
        testDomain.add(-3.0);
        testDomain.add(3.0);
        testConst.put("a", 0.5);
        testConst.put("b", 1.5);
        testConst.put("c", -0.5);
        testConst.put("d", 2.0);
        testConst.put("e", 0.5);
        testConst.put("f", -2.5);
        testConst.put("g", 0.0);


        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * sin(testConst.get("b") * x)
                         + testConst.get("c") * cos(testConst.get("d") * x)
                         + testConst.get("e") * tan(testConst.get("f") * x) + testConst.get("g");
        assertEquals(expectedY, func.evalAtX(x), Function.DELTA / 10.0);
    }

    @Test
    public void testEvalAtXLogarithmic() {
        testType = "trigonometric";
        testDomain.add(-3.0);
        testDomain.add(3.0);
        testConst.put("a", 1.5);
        testConst.put("b", 3.0);
        testConst.put("c", -0.5);


        Function func = new Function(testType, testConst, testDomain);

        double x = 0.7389;
        double expectedY = testConst.get("a") * log(testConst.get("b") * x) + testConst.get("c");
        assertEquals(expectedY, func.evalAtX(x), Function.DELTA / 10.0);
    }
}