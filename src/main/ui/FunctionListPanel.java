package ui;

import model.Function;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Scroll-able panel that contains a list of all the information related to each
// function in the workspace and displays them
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class FunctionListPanel extends JScrollPane {
    public static final int WIDTH = FunctionLabel.WIDTH;
    public static final int HEIGHT = 350;
    public static final int SCROLL_BAR_SENSITIVITY = 6; // in pixels

    private JPanel internalPanel; //internal panel of JScrollPane
    private List<FunctionLabel> funcLabels;

    // EFFECTS: sets up this (which is a JScrollPane) and sub-labels, which each contain
    //          the information for each function (see FunctionLabel.java)
    public FunctionListPanel() {
        setupInternalPanel();
        getVerticalScrollBar().setUnitIncrement(SCROLL_BAR_SENSITIVITY);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(true);
        funcLabels = new ArrayList<>();

        //TEST
        //testLabels();
        //TEST
    }

    // MODIFIES: this
    // EFFECTS: sets up the internal panel contained by FunctionListPanel (which is a JScrollPane)
    private void setupInternalPanel() {
        internalPanel = new JPanel();
        internalPanel.setLayout(new FlowLayout());
        internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.PAGE_AXIS));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setViewportView(internalPanel);
    }

    // REQUIRES: name must not be an empty string, same REQUIRES as Function constructor
    // MODIFIES: this, FunctionLabel
    // EFFECTS: creates new FunctionLabel with a name, function type, domain, and constants
    public void addNewFuncLabel(String name, String type, List<Double> domain,
                                HashMap<String, Double> constants) {
        FunctionLabel funcLabel = new FunctionLabel(name, type, domain, constants);
        funcLabels.add(funcLabel);
        internalPanel.add(funcLabel);
        internalPanel.revalidate();
    }

    // REQUIRES: name must not be an empty string
    // MODIFIES: this, FunctionLabel
    // EFFECTS: creates new FunctionLabel with a name and function object directly
    public void addNewFuncLabel(String name, Function function) {
        FunctionLabel funcLabel = new FunctionLabel(name, function.getFunctionType(),
                function.getDomain(), function.getConstants());
        funcLabels.add(funcLabel);
        internalPanel.add(funcLabel);
        internalPanel.revalidate();
    }

    // REQUIRES: "name" Function object exists in funcLabels
    // MODIFIES: this, FunctionLabel
    // EFFECTS: removes the function label from this
    public void removeFuncLabel(String name) {
        FunctionLabel labelToRemove = null;
        for (FunctionLabel label : funcLabels) {
            if (label.getFunctionName().equals(name)) {
                labelToRemove = label;
            }
        }

        // have to perform the deletion outside of the loop,
        // otherwise it causes a ConcurrentModificationException
        funcLabels.remove(labelToRemove);
        internalPanel.remove(labelToRemove);
        internalPanel.revalidate();
    }

    //TEST
    private void testLabels() {
        JTextArea label1 = new JTextArea("text 1 \nsdfsafsadfsadfa");
        label1.setPreferredSize(new Dimension(200, 100));
        label1.setEditable(false);
        //label1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label1.setBackground(new Color(146, 161, 208));
        label1.setOpaque(true);
        JTextArea label2 = new JTextArea("text 2  \nsdfasfsadfadsfasdf");
        label2.setPreferredSize(new Dimension(200, 100));
        label2.setEditable(false);
        //label2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label2.setBackground(new Color(186, 201, 248));
        label2.setOpaque(true);
        JTextArea label3 = new JTextArea("text 3                    \nsadfsadfadsdfsadfas");
        label3.setPreferredSize(new Dimension(200, 100));
        label3.setEditable(false);
        //label3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label3.setBackground(new Color(146, 161, 208));
        label3.setOpaque(true);
        JTextArea label4 = new JTextArea("text 4       asdfasdfasd             \nsadfsadfadsdfsadfas");
        label4.setPreferredSize(new Dimension(200, 100));
        label4.setEditable(false);
        //label4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label4.setBackground(new Color(186, 201, 248));
        label4.setOpaque(true);
        JTextArea label5 = new JTextArea("text 5       asdfasdfasd    sdfafdsafadsfsad         "
                + "\nsadfsadfadsdfsadfas");
        label5.setPreferredSize(new Dimension(200, 100));
        label5.setEditable(false);
        //label5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label5.setBackground(new Color(146, 161, 208));
        label5.setOpaque(true);

        internalPanel.add(label1);
        internalPanel.add(label2);
        internalPanel.add(label3);
        internalPanel.add(label4);
        internalPanel.add(label5);
    }
}
