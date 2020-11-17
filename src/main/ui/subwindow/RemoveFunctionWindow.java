package ui.subwindow;

import ui.FunctionPlotterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Sub-window that handles removing a function from the workspace
// (invoked when the "Remove Function" button is clicked in FunctionPlotterGUI)
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

    // REQUIRES: main is main GUI JFrame, all function names in funcNames exist
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

    private void initializePanels(String[] funcNames) {
        framePanel = new JPanel();
        framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
        initializeComboBoxPanel(funcNames);
        initializeRemoveButtonPanel();
        initializeConfirmPanel();
    }

    private void initializeComboBoxPanel(String[] funcNames) {
        comboBoxPanel = new JPanel();
        comboBoxPanel.add(new JLabel("Select the function to remove:"));
        comboBox = new JComboBox<>(funcNames);
        comboBox.setPreferredSize(new Dimension(SUB_FIELD_WIDTH, SUB_FIELD_HEIGHT));
        comboBoxPanel.add(comboBox);
        comboBoxPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        framePanel.add(comboBoxPanel);
    }

    private void initializeRemoveButtonPanel() {
        removeButtonPanel = new JPanel();
        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);
        removeButtonPanel.add(removeButton);
        removeButtonPanel.setPreferredSize(new Dimension(SUB_PANEL_WIDTH, SUB_PANEL_HEIGHT));
        removeButtonPanel.setVisible(true);
        framePanel.add(removeButtonPanel);
    }

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
