package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.Double.NaN;
import static java.lang.Math.*;


// Represents an individual mathematical function, containing information
// on it's definition and containing the capability to return x and y values
public class Function {
    public static final double DELTA = 0.25; //x-value spacing
    public static final String TYPE_LINEAR = "linear";
    public static final String TYPE_POLY = "polynomial";
    public static final String TYPE_EXP = "exponential";
    public static final String TYPE_TRIG = "trigonometric";
    public static final String TYPE_LOG = "logarithmic";
    public static final String[] CONSTANT_NAMES = {"a","b","c","d","e","f","g"};
    private static final int HASH_MULTIPLIER = 73; //prime number used for hashCode()
    private static final HashMap<String, Integer> NUMBER_OF_CONSTANTS_FOR_TYPE = new HashMap<>();

    private final String functionType;
    private HashMap<String, Double> constants;
    private List<Double> domain;
    private List<Double> valuesY;
    private List<Double> valuesX; //calculated using domain and DELTA

    // REQUIRES: - type must be one of the following strings:
    //                  "linear" -> a*x + b,
    //                  "polynomial" -> a*x^5 + b*x^4 + c*x^3 + d*x^2 + e*x + f,
    //                  "exponential" -> a*e^(b*x) + c,
    //                  "trigonometric" -> a*sin(b*x) + c*cos(d*x) + e*tan(f*x) + g,
    //                  "logarithmic" -> a*ln(b*x) + c
    //                  ***see the constant TYPE's above
    //           - constants must be a HashMap of all constants in the function
    //           - domainX must be a List where index 0 is left domain boundary, index 1 is right domain boundary
    //           - points in pointsX must be equally-spaced
    // MODIFIES: this
    // EFFECTS: creates a Function object with a function type specified, as well as its constants and domain;
    //          calculates valuesX and valuesY
    public Function(String type, HashMap<String, Double> funcConstants, List<Double> domainX) {
        //initialize simple fields
        functionType = type;
        constants = funcConstants;
        initNumberOfConstantsForType();
        domain = domainX;
        valuesX = new ArrayList<>();
        valuesY = new ArrayList<>();

        //generate x and y values
        double i = domain.get(0);
        while (i <= domain.get(1)) {
            valuesX.add(i);
            valuesY.add(evalFunction(i));
            i += Function.DELTA;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes HashMap containing mappings between function type and their
    //          respective number of constants in the function definition
    private void initNumberOfConstantsForType() {
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_LINEAR, 2);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_POLY, 6);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_EXP, 3);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_TRIG, 7);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_LOG, 3);
    }

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: returns the function evaluated at x (i.e. returns f(x)) by calling appropriate "eval" method
    public double evalFunction(double x) {
        switch (functionType) {
            case TYPE_LINEAR:
                return evalLinear(x);
            case TYPE_POLY:
                return evalPolynomial(x);
            case TYPE_EXP:
                return evalExponential(x);
            case TYPE_TRIG:
                return evalTrigonometric(x);
            case TYPE_LOG:
                return evalLogarithmic(x);
            default:
                return NaN;
        }
    }

    // ~~~~~~~~~~GETTERS~~~~~~~~~~~

    // EFFECTS: returns functionType
    public String getFunctionType() {
        return functionType;
    }

    // EFFECTS: returns constants
    public HashMap<String, Double> getConstants() {
        return constants;
    }

    // EFFECTS: returns domain
    public List<Double> getDomain() {
        return domain;
    }

    // EFFECTS: returns valuesX
    public List<Double> getValuesX() {
        return valuesX;
    }

    // EFFECTS: returns valuesY
    public List<Double> getValuesY() {
        return valuesY;
    }

    // ~~~~~~~~~~~PRIVATE METHODS~~~~~~~~~~~~~

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: evaluates a function defined by the type TYPE_LINEAR
    private double evalLinear(double x) {
        return constants.get("a") * x + constants.get("b");
    }

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: evaluates a function defined by the type TYPE_POLY
    private double evalPolynomial(double x) {
        return constants.get("a") * x * x * x * x * x + constants.get("b") * x * x * x * x
                + constants.get("c") * x * x * x + constants.get("d") * x * x
                + constants.get("e") * x + constants.get("f");
    }

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: evaluates a function defined by the type TYPE_EXP
    private double evalExponential(double x) {
        return constants.get("a") * exp(constants.get("b") * x) + constants.get("c");
    }

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: evaluates a function defined by the type TYPE_TRIG
    private double evalTrigonometric(double x) {
        return constants.get("a") * sin(constants.get("b") * x)
                + constants.get("c") * cos(constants.get("d") * x)
                + constants.get("e") * tan(constants.get("f") * x) + constants.get("g");
    }

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: evaluates a function defined by the type TYPE_LOG
    private double evalLogarithmic(double x) {
        return constants.get("a") * log(constants.get("b") * x) + constants.get("c");
    }

    // REQUIRES: a Function object with name "name" exists in the corresponding Workspace
    // EFFECTS: returns Function object as JSONObject
    public JSONObject toJson(String name) {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("type", functionType);
        json.put("constants", constantsToJson());
        json.put("domain", domain);
        json.put("valuesX", valuesX);
        json.put("valuesY", valuesY);

        return json;
    }

    // EFFECTS: returns constants as JSONArray (for toJson() method)
    private JSONObject constantsToJson() {
        JSONObject constantsJson = new JSONObject();

        for (int i = 0; i < NUMBER_OF_CONSTANTS_FOR_TYPE.get(functionType); i++) {
            constantsJson.put(CONSTANT_NAMES[i], constants.get(CONSTANT_NAMES[i]));
        }

        return constantsJson;
    }

    @Override
    // EFFECTS: returns true if this and o are equal by each field, false otherwise
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Function function = (Function) o;
            return functionType.equals(function.functionType)
                    && constants.equals(function.constants)
                    && domain.equals(function.domain)
                    && valuesY.equals(function.valuesY)
                    && valuesX.equals(function.valuesX);
        }
    }

    @Override
    // EFFECTS: generates a hash code for this
    public int hashCode() {
        return Objects.hash(functionType, constants, domain, valuesY, valuesX);
    }
}
