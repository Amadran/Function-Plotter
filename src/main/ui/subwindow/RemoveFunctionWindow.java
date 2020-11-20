package ui.subwindow;

import ui.FunctionPlotterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Sub-window that handles removing a function from the workspace
// (invoked when the "Remove Function" button is clicked in FunctionPlotterGUI)
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class RemoveFunctionWindow extends JFrame implements ActionListener {
    private static final int SUB_PANEL_HEIGHT = 50;
    private static final int SUB_PANEL_WIDTH = 200;
    private static final int SUB_FIELD_HEIGHT = 30;
    private static final int SUB_FIELD_WIDTH = 150;

    private FunctionPlotterGUI mainFrame; //to pass information back up to the main JFrame
    private JPanel framePanel; //needed to be able to use BoxLayout (can't set this
                               //as this AddFunctionWindow's layout manager)
    private JPanel comboBoxPanel;
    private JComboBox<String> comboBox;
    private JPanel removeButtonPanel;
    private JButton removeButton;
    private JPanel confirmPanel;
    private JButton yesButton;
    private JButton noButton;

    // REQUIRES: main is main GUI JFrame, funcNames are function names in the active workspace
    // EFFECTS: constructs and shows the window to select which function to remove from the workspace
    public RemoveFunctionWindow(FunctionPlotterGUI main, String[] funcNames) {
        mainFrame = main;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        initializePanels(funcNames);
        add(framePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // REQUIRES: funcNames are function names in the active workspace
    // MODIFIES: this, all fields (panels and buttons)
    // EFFECTS: initializes all of the sub-panels
    private void initializePanels(String[] funcNames) {
        framePanel = new JPanel();
        framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
        initializeComboBoxPanel(funcNames);
        initializeRemoveButtonPanel();
        initializeConfirmPanel();
    }

    // REQUIRES: funcNames are function names in the active workspace
    // MODIFIES: this, comboBoxPanel, comboBox, framePanel
    // EFFECTS: sets up the combo box panel, which allows selection of the functions in the workspace
    private void initializeComboBoxPanel(String[] funcNames) {
        comboBoxPanel = new JPanel();
        comboBoxPanel.add(new JLabel("Select the function to remove:"));
        comboBox = new JComboBox<>(funcNames);
        comboBox.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));
        comboBoxPanel.add(comboBox);
        comboBoxPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        framePanel.add(comboBoxPanel);
    }

    // MODIFIES: this, removeButtonPanel, removeButton, framePanel
    // EFFECTS: sets up the panel containing the button to remove selected function
    private void initializeRemoveButtonPanel() {
        removeButtonPanel = new JPanel();
        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);
        removeButtonPanel.add(removeButton);
        removeButtonPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        removeButtonPanel.setVisible(true);
        framePanel.add(removeButtonPanel);
    }

    // MODIFIES: this, confirmPanel, yesButton, noButton, framePanel
    // EFFECTS: sets up the panel containing confirmation (yes/no) buttons for removing function
    private void initializeConfirmPanel() {
        confirmPanel = new JPanel();
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        confirmPanel.add(yesButton);
        confirmPanel.add(noButton);
        confirmPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        confirmPanel.setVisible(false);
        framePanel.add(confirmPanel);
    }

    @Override
    // MODIFIES: this, removeButtonPanel, confirmPanel, workspace, funcListPanel, canvasPanel, FunctionLabel
    // EFFECTS: replaces removeButton with confirmation buttons upon click, removes function on
    //          click of yesButton, or does nothing on click of noButton
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeButton) {
            removeButtonPanel.setVisible(false);
            confirmPanel.setVisible(true);
        } else if (e.getSource() == yesButton) {
            mainFrame.removeFunction(comboBox.getSelectedItem().toString());
            dispose();
        } else if (e.getSource() == noButton) {
            dispose();
        }
    }
}
