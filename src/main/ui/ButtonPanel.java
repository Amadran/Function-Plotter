package ui;

import ui.subwindow.AddFunctionWindow;
import ui.subwindow.RemoveFunctionWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Panel containing all the buttons of the GUI
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class ButtonPanel extends JPanel implements ActionListener {
    public static final int WIDTH = FunctionListPanel.WIDTH;
    public static final int HEIGHT = CanvasPanel.HEIGHT - FunctionListPanel.HEIGHT;

    private FunctionPlotterGUI mainFrame; //so information can be sent back up to FunctionPlotterGUI (JFrame)
    private JButton addFuncButton;
    private JButton removeFuncButton;
    private JButton evalFuncButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    // REQUIRES: main must be the parent FunctionPlotterGUI object
    // EFFECTS: sets up ButtonPanel and its buttons
    public ButtonPanel(FunctionPlotterGUI main) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(142, 213, 165));
        setOpaque(true);
        setLayout(new GridLayout(2, 3, 5, 5));
        initializeButtons();
        mainFrame = main;
    }

    // MODIFIES: this
    // EFFECTS: initializes all the buttons contained in ButtonPanel
    private void initializeButtons() {
        instantiateAllButtons();
        addAllActionListeners();
        setAllNotFocusable();
        addAllButtonsToPanel();
    }

    // MODIFIES: this
    // EFFECTS: instantiates all the buttons
    private void instantiateAllButtons() {
        addFuncButton = new JButton("Add Function");
        removeFuncButton = new JButton("Remove Function");
        evalFuncButton = new JButton("Evaluate Function");
        saveButton = new JButton("Save Workspace");
        loadButton = new JButton("Load Workspace");
        quitButton = new JButton("Quit");
    }

    // MODIFIES: this
    // EFFECTS: adds this as action listener to all the buttons
    private void addAllActionListeners() {
        addFuncButton.addActionListener(this);
        removeFuncButton.addActionListener(this);
        evalFuncButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets all buttons to not focusable
    private void setAllNotFocusable() {
        addFuncButton.setFocusable(false);
        removeFuncButton.setFocusable(false);
        evalFuncButton.setFocusable(false);
        saveButton.setFocusable(false);
        loadButton.setFocusable(false);
        quitButton.setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: adds all the buttons to this
    private void addAllButtonsToPanel() {
        add(addFuncButton);
        add(removeFuncButton);
        add(evalFuncButton);
        add(saveButton);
        add(loadButton);
        add(quitButton);
    }

    @Override
    // EFFECTS: performs the relevant actions for each button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFuncButton) {
            System.out.println("add");
            new AddFunctionWindow(mainFrame);
        } else if (e.getSource() == removeFuncButton) {
            System.out.println("remove");
            new RemoveFunctionWindow(mainFrame, mainFrame.getFunctionNames());
        } else if (e.getSource() == evalFuncButton) {
            System.out.println("evaluate");
        } else if (e.getSource() == saveButton) {
            mainFrame.saveWorkspace();
            System.out.println("save");
        } else if (e.getSource() == loadButton) {
            mainFrame.loadWorkspace();
            System.out.println("load");
        } else if (e.getSource() == quitButton) {
            System.out.println("quit");
        }
    }
}
