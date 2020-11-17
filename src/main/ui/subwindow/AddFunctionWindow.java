package ui.subwindow;

import model.Function;
import ui.FunctionPlotterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Sub-window that handles adding a new function to the workspace
// (invoked when the "Add Function" button is clicked in FunctionPlotterGUI)
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class AddFunctionWindow extends JFrame implements ActionListener {
    private static final int SUB_PANEL_HEIGHT = 50;
    private static final int SUB_PANEL_WIDTH = 200;
    private static final int CONSTANTS_LABEL_WIDTH = 20;
    private static final int CONSTANTS_LABEL_HEIGHT = 40;
    private static final int SUB_FIELD_HEIGHT = 30;
    private static final int SUB_FIELD_WIDTH = 150;
    private static final String[] FUNC_TYPES = {Function.TYPE_LINEAR, Function.TYPE_POLY, Function.TYPE_EXP,
                                                Function.TYPE_TRIG, Function.TYPE_LOG};
    private static final HashMap<String, Integer> NUMBER_OF_CONSTANTS_FOR_TYPE = new HashMap<>();

    private FunctionPlotterGUI mainFrame; // to pass information back up to the main JFrame
    private JPanel framePanel; // needed to be able to use BoxLayout (can't set AddFunctionWindow's
                               // layout manager as BoxLayout)
    private JPanel namePanel;
    private JPanel typePanel;
    private JComboBox<String> typePanelComboBox; // to be able to refer to this in actionPerformed()
    private JPanel domainPanel;
    private JPanel constantsPanel;
    private JLabel constantsLabel; // label that is outside the sub-panels in constantsPanel
    private JPanel submitPanel;
    private JButton submitButton;

    // REQUIRES: main must be the parent FunctionPlotterGUI object
    // EFFECTS: creates the AddFunctionWindow sub-window and its sub-panels
    public AddFunctionWindow(FunctionPlotterGUI main) {
        mainFrame = main;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        initNumberOfConstantsForType();
        initializePanels();
        add(framePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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

    // EFFECTS: returns the highest int value in a string -> integer hashMap
    private int hashMapMaxValue(HashMap<String, Integer> hashMap) {
        int max = 0;
        for (String key : hashMap.keySet()) {
            if (hashMap.get(key) > max) {
                max = hashMap.get(key);
            }
        }
        return max;
    }

    // MODIFIES: this
    // EFFECTS: initializes all of the sub-panels of this sub-window
    private void initializePanels() {
        framePanel = new JPanel();
        framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
        initializeNamePanel();
        initializeTypePanel();
        initializeDomainPanel();
        initializeConstantsPanel();
        initializeSubmitPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that handles specifying the name for the function
    private void initializeNamePanel() {
        namePanel = new JPanel();
        namePanel.add(new JLabel("Function Name:"));
        JTextField textField = new JTextField("my linear function");
        textField.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));

        namePanel.add(textField);
        namePanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        //namePanel.setLayout(new BorderLayout());
        namePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        framePanel.add(namePanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that handles specifying the type for the function
    private void initializeTypePanel() {
        typePanel = new JPanel();
        typePanel.add(new JLabel("Function Type:"));
        typePanelComboBox = new JComboBox<>(FUNC_TYPES);
        typePanelComboBox.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));
        typePanelComboBox.addActionListener(this);

        typePanel.add(typePanelComboBox);
        typePanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        //typePanel.setLayout(new BorderLayout());
        typePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        framePanel.add(typePanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that handles specifying the domain for the function
    private void initializeDomainPanel() {
        domainPanel = new JPanel();
        domainPanel.add(new JLabel("Domain:"));
        JTextField leftBoundary = new JTextField("-2.0");
        leftBoundary.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));
        JTextField rightBoundary = new JTextField("2.0");
        rightBoundary.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));

        domainPanel.add(leftBoundary);
        domainPanel.add(rightBoundary);
        domainPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        //domainPanel.setLayout(new BorderLayout());
        domainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        framePanel.add(domainPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that handles specifying the constants for the function
    private void initializeConstantsPanel() {
        int maxNumOfConstants = hashMapMaxValue(NUMBER_OF_CONSTANTS_FOR_TYPE);

        constantsLabel = new JLabel("Constants:");
        constantsLabel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        constantsPanel = new JPanel();
        constantsPanel.setLayout(new GridLayout(maxNumOfConstants / 2 + 1, 2, 0, 0));
        //constantsPanel.setLayout(new FlowLayout());
        constantsPanel.add(constantsLabel);

        for (int i = 0; i < maxNumOfConstants; i++) {
            JPanel constantsField = new JPanel();
            JLabel label = new JLabel(Function.CONSTANT_NAMES[i] + ":");
            label.setPreferredSize(new Dimension(CONSTANTS_LABEL_WIDTH, CONSTANTS_LABEL_HEIGHT));
            JTextField field = new JTextField();
            field.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));
            constantsField.add(label);
            constantsField.add(field);
            constantsPanel.add(constantsField);
        }

        hideConstantFields(NUMBER_OF_CONSTANTS_FOR_TYPE.get(typePanelComboBox.getSelectedItem().toString()));
        constantsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        framePanel.add(constantsPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that contains the submit button
    private void initializeSubmitPanel() {
        submitPanel = new JPanel();
        submitButton = new JButton("Add Function");
        submitButton.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));
        submitButton.addActionListener(this);
        submitPanel.add(submitButton);
        framePanel.add(submitPanel);
    }

    // REQUIRES: numConstants is one of the values in NUMBER_OF_CONSTANTS_FOR_TYPE
    // MODIFIES: this
    // EFFECTS: hides all the constant-specifying fields numbered after numConstants
    private void hideConstantFields(int numConstants) {
        int maxNumConstants = hashMapMaxValue(NUMBER_OF_CONSTANTS_FOR_TYPE);
        Component[] components = constantsPanel.getComponents();

        for (int i = 1; i < maxNumConstants + 1; i++) {
            components[i].setVisible(true);
            if (i >= numConstants + 1) {
                components[i].setVisible(false);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that handles specifying the domain for the function
    private HashMap<String, Double> getConstants(int numConstants) {
        HashMap<String, Double> constants = new HashMap<>();
        Component[] comps = constantsPanel.getComponents();

        //index 0 is a label, all subpanels are indices 1 to numConstants (which has a max of
        //hashMapMaxValue(NUMBER_OF_CONSTANTS_FOR_TYPE) == 7 currently)
        for (int i = 1; i <= numConstants; i++) {
            JPanel constantsSubPanel = (JPanel) comps[i];
            JTextField constantsField = (JTextField) constantsSubPanel.getComponent(1);
            constants.put(Function.CONSTANT_NAMES[i - 1], Double.parseDouble(constantsField.getText()));
        }

        return constants;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == typePanelComboBox) {
            String selectedItem = typePanelComboBox.getSelectedItem().toString();
            int numToHide = NUMBER_OF_CONSTANTS_FOR_TYPE.get(selectedItem);
            hideConstantFields(numToHide);
        } else if (e.getSource() == submitButton) {
            JTextField nameField = (JTextField) namePanel.getComponent(1);
            String type = typePanelComboBox.getSelectedItem().toString();
            List<Double> domain = new ArrayList<>();
            JTextField leftField = (JTextField) domainPanel.getComponent(1);
            JTextField rightField = (JTextField) domainPanel.getComponent(2);
            domain.add(Double.parseDouble(leftField.getText()));
            domain.add(Double.parseDouble(rightField.getText()));

            int numConstants = NUMBER_OF_CONSTANTS_FOR_TYPE.get(typePanelComboBox.getSelectedItem().toString());
            mainFrame.addFunction(nameField.getText(), type, getConstants(numConstants), domain);
            dispose();
        }
    }
}
