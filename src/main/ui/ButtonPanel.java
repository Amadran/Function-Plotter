package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The panel containing all the buttons of this program's GUI
public class ButtonPanel extends JPanel implements ActionListener {
    public static final int WIDTH = FunctionListPanel.WIDTH;
    public static final int HEIGHT = CanvasPanel.HEIGHT - FunctionListPanel.HEIGHT;

    private FunctionPlotterGUI mainFrame; // so information can be sent back up to the parent JFrame
    private JButton addFuncButton;
    private JButton removeFuncButton;
    private JButton evalFuncButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    // REQUIRES: main must be the parent JFrame
    // EFFECTS: sets up this' properties and all of its JButton's
    public ButtonPanel(FunctionPlotterGUI main) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(142, 213, 165));
        setOpaque(true);
        setLayout(new GridLayout(2, 3, 5, 5));
        initializeButtons();
        mainFrame = main;
    }

    // MODIFIES: this
    // EFFECTS: initializes all the buttons contained in this panel
    public void initializeButtons() {
        instantiateAllButtons();
        addAllActionListeners();
        setAllNotFocusable();
        addAllButtonsToPanel();
    }

    // MODIFIES: this
    // EFFECTS: instantiates all the buttons
    public void instantiateAllButtons() {
        addFuncButton = new JButton("Add Function");
        removeFuncButton = new JButton("Remove Function");
        evalFuncButton = new JButton("Evaluate Function");
        saveButton = new JButton("Save Workspace");
        loadButton = new JButton("Load Workspace");
        quitButton = new JButton("Quit");
    }

    // MODIFIES: this
    // EFFECTS: adds this as action listener to all the buttons
    public void addAllActionListeners() {
        addFuncButton.addActionListener(this);
        removeFuncButton.addActionListener(this);
        evalFuncButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets all buttons to not focusable
    public void setAllNotFocusable() {
        addFuncButton.setFocusable(false);
        removeFuncButton.setFocusable(false);
        evalFuncButton.setFocusable(false);
        saveButton.setFocusable(false);
        loadButton.setFocusable(false);
        quitButton.setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: adds all the buttons to this
    public void addAllButtonsToPanel() {
        add(addFuncButton);
        add(removeFuncButton);
        add(evalFuncButton);
        add(saveButton);
        add(loadButton);
        add(quitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addFuncButton) {
            System.out.println("add");
        } else if (e.getSource() == removeFuncButton) {
            System.out.println("remove");
        } else if (e.getSource() == evalFuncButton) {
            System.out.println("evaluate");
        } else if (e.getSource() == saveButton) {
            System.out.println("save");
        } else if (e.getSource() == loadButton) {
            System.out.println("load");
        } else if (e.getSource() == quitButton) {
            System.out.println("quit");
        }
    }
}
