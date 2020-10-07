package model;

import java.util.HashMap;
import java.util.List;


// Function class represents an individual mathematical function, containing information
// on it's definition and containing the capability to return x and y values
public class Function {
    public static final double DELTA = 1e-4; //want all functions to have same x-spacing

    private String functionType;
    private HashMap<String, Double> constants;
    private List<Double> domain;
    private List<Double> valuesY;
    private List<Double> valuesX; //calculated using domain and DELTA, stored for performance

    // REQUIRES: - type must be one of the following strings:
    //                  "linear" -> a*x + b,
    //                  "polynomial" -> a*x^5 + b*x^4 + c*x^3 + d*x^2 + e*x + f,
    //                  "exponential" -> a*e^(b*x) + c,
    //                  "trigonometric" -> a*sin(b*x) + c*cos(d*x) + e*tan(f*x) + g,
    //                  "logarithmic" -> a*ln(b*x) + c
    //           - constants must be a HashMap of all constants in the function
    //           - domainLeft < domainRight
    //           - points in pointsX must be equally-spaced
    // MODIFIES: this
    // EFFECTS: creates a Function object with a function type specified, as well as its constants and domain;
    //          calculates valuesX and valuesY
    public Function(String type, HashMap<String, Double> constants, List<Double> domainX) {
        //stub
    }

    // REQUIRES: x must be in the domain of the function
    // EFFECTS: returns the function evaluated at x (i.e. returns f(x))
    public double evalAtX(double x) {
        return 0.0; //stub
    }

    // ~~~~~~~~~~GETTERS~~~~~~~~~~~

    public String getFunctionType() {
        return functionType;
    }

    public HashMap<String, Double> getConstants() {
        return constants;
    }

    public List<Double> getDomain() {
        return domain;
    }

    public List<Double> getValuesX() {
        return valuesX;
    }

    public List<Double> getValuesY() {
        return valuesY;
    }
}
