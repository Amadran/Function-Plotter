package ui;

import model.Function;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

// Panel of an individual entry in the list of functions (FunctionListPanel)
// ***needs to extend JTextArea so that text can wrap to the next line
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class FunctionLabel extends JTextArea {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 100;
    private static final String FUNC_EXPRESSION_LINEAR = "a*x + b";
    private static final String FUNC_EXPRESSION_POLY = "a*x^5 + b*x^4 + c*x^3 + d*x^2 + e*x + f";
    private static final String FUNC_EXPRESSION_EXP = "a*e^(b*x) + c";
    private static final String FUNC_EXPRESSION_TRIG = "a*sin(b*x) + c*cos(d*x) + e*tan(f*x) + g";
    private static final String FUNC_EXPRESSION_LOG = "a*ln(b*x) + c";

    private String name;
    private String type;
    private List<Double> domain;
    private HashMap<String, Double> constants;

    // REQUIRES: name must not be an empty string, same REQUIRES as Function constructor
    // EFFECTS: creates a FunctionLabel to display information of an individual function in the GUI
    public FunctionLabel(String name, String type, List<Double> domain,
                         HashMap<String, Double> constants) {
        setFields(name, type, domain, constants);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setEditable(false);
        //setBackground(new Color(167, 142, 199));
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        setOpaque(true);
    }

    // REQUIRES: name must not be an empty string, same REQUIRES as Function constructor
    // MODIFIES: this
    // EFFECTS: sets the text of this
    private void setFields(String name, String type, List<Double> domain,
                          HashMap<String, Double> constants) {
        this.name = name;
        this.type = type;
        this.domain = domain;
        this.constants = constants;

        String constantsString = "";
        for (String constKey : constants.keySet()) {
            constantsString += constants.get(constKey) + ", ";
        }
        constantsString = constantsString.substring(0, constantsString.length() - 2); //remove trailing ", "

        String thing = "Name: " + name
                + "\nType: " + type
                + "\nExpression: " + convertTypeToFunctionExpression(type)
                + "\nDomain: [" + domain.get(0) + ", " + domain.get(1) + "]"
                + "\nConstants: [" + constantsString + "]";

        System.out.println(thing);
        setText(thing);
    }

    // REQUIRES: type is a valid Function type string
    // EFFECTS: converts Function type string into a human-readable expression of the math function
    private String convertTypeToFunctionExpression(String type) {
        switch (type) {
            case Function.TYPE_LINEAR:
                return FUNC_EXPRESSION_LINEAR;
            case Function.TYPE_POLY:
                return FUNC_EXPRESSION_POLY;
            case Function.TYPE_EXP:
                return FUNC_EXPRESSION_EXP;
            case Function.TYPE_TRIG:
                return FUNC_EXPRESSION_TRIG;
            case Function.TYPE_LOG:
                return FUNC_EXPRESSION_LOG;
            default:
                return null; //compiler complains of missing return statement if this is excluded
        }
    }

    // EFFECTS: returns the name of the function label
    public String getFunctionName() {
        return name;
    }
}
