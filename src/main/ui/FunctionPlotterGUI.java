package ui;

import model.Workspace;

import javax.swing.*;
import java.awt.*;

// The main GUI window for the program
public class FunctionPlotterGUI extends JFrame {
    private Workspace workspace;
    private CanvasPanel canvasPanel;
    private FunctionListPanel funcPanel;
    private ButtonPanel buttonPanel;
    private JPanel optionsPanel; //intermediate panel containing funcPanel and buttonPanel

    // EFFECTS: sets up underlying JFrame properties of the GUI, initializes all the sub-panels
    public FunctionPlotterGUI() {
        super("Function Plotter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        workspace = new Workspace();
        initializePanels();

        pack();
        setLocationRelativeTo(null);
        //setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this, CanvasPanel, FunctionListPanel, ButtonPanel
    // EFFECTS: initializes the sub-panels of the main GUI JFrame,
    public void initializePanels() {
        //initialize each panel
        canvasPanel = new CanvasPanel();
        funcPanel = new FunctionListPanel();
        buttonPanel = new ButtonPanel(this);

        //initialize intermediate panel containing funcPanel and buttonPanel
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout());

        //TEST
        optionsPanel.setBackground(Color.red);
        //TEST

        optionsPanel.setOpaque(true);
        optionsPanel.add(funcPanel, BorderLayout.CENTER);
        optionsPanel.add(buttonPanel, BorderLayout.SOUTH);

        //add main panels to JFrame (this)
        add(canvasPanel, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.EAST);
    }
}
