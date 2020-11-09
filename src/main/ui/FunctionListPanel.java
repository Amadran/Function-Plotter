package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// The scroll-able panel that contains all of the textual information related to each
// Function object in the Workspace
public class FunctionListPanel extends JScrollPane {
    public static final int WIDTH = FunctionLabel.WIDTH;
    public static final int HEIGHT = 350;
    public static final int SCROLL_BAR_SENSITIVITY = 6; // in pixels

    private JPanel internalPanel; //JScrollPane is a container, so it needs a component as a child
    private List<FunctionLabel> funcLabels;

    // EFFECTS: sets up underlying JScrollPane properties and the sub-labels, which each contain
    //          the information for each Function object (see FunctionLabel.java)
    public FunctionListPanel() {
        setupInternalPanel();
        getVerticalScrollBar().setUnitIncrement(SCROLL_BAR_SENSITIVITY);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(true);
        funcLabels = new ArrayList<>();

        //TEST
        testLabels();
        //TEST
    }

    // MODIFIES: this
    // EFFECTS: sets up the internal panel contained by FunctionListPanel (which is a JScrollPane)
    public void setupInternalPanel() {
        internalPanel = new JPanel();
        internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.PAGE_AXIS));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setViewportView(internalPanel);
    }

    // MODIFIES: this, FunctionLabel
    // EFFECTS: forwards information contained in each FunctionLabel directly them via FunctionLabel.setFields()
    public void setFuncLabelFields(String name, String expression, String type, List<Double> domain,
                                   HashMap<String, Double> constants) {
        for (FunctionLabel label : funcLabels) {
            label.setFields(name, expression, type, domain, constants);
            add(label);
        }
    }

    //TEST
    public void testLabels() {
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
