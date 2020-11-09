package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

// Represents the panel of an individual entry in the list of functions
// ***needs to be JTextArea so that text can properly wrap to the next line
public class FunctionLabel extends JTextArea {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 100;

    // EFFECTS: sets up this' properties
    public FunctionLabel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setEditable(false);
        setBackground(new Color(167, 142, 199));
        setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the text of this
    public void setFields(String name, String expression, String type, List<Double> domain,
                          HashMap<String, Double> constants) {
        String constantsString = null;
        for (String constKey : constants.keySet()) {
            constantsString += constants.get(constKey);
        }

        setText("Name: " + name
                + "\nType: " + type
                + "\nExpression: " + expression
                + "\nDomain: [" + domain.get(0) + ", " + domain.get(1) + "]"
                + "\nConstants: [" + constantsString);
    }
}
