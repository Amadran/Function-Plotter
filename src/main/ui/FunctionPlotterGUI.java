package ui;

import model.Function;
import model.Workspace;
import ui.subwindow.WorkspaceFileHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Main GUI window for the program, containing all the sub-panels
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class FunctionPlotterGUI extends JFrame {
    private Workspace workspace;
    private CanvasPanel canvasPanel;
    private FunctionListPanel funcListPanel;
    private ButtonPanel buttonPanel;
    private JPanel optionsPanel; //intermediate panel, containing funcPanel and buttonPanel, to facilitate layout
    private WorkspaceFileHandler fileHandler;

    // EFFECTS: sets up main GUI JFrame, initializes all the sub-panels
    public FunctionPlotterGUI() {
        super("Function Plotter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        workspace = new Workspace();
        fileHandler = new WorkspaceFileHandler(this);
        initializePanels();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the all the sub-panels
    private void initializePanels() {
        //initialize each panel
        canvasPanel = new CanvasPanel(workspace);
        funcListPanel = new FunctionListPanel();
        buttonPanel = new ButtonPanel(this);

        //initialize intermediate panel containing funcPanel and buttonPanel
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setOpaque(true);
        optionsPanel.add(funcListPanel, BorderLayout.CENTER);
        optionsPanel.add(buttonPanel, BorderLayout.SOUTH);

        //add main panels to JFrame (this)
        add(canvasPanel, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.EAST);
    }

    // REQUIRES: name must not be an empty string, same REQUIRES as Function constructor
    // MODIFIES: this
    // EFFECTS: adds a Function to the workspace and the funcListPanel
    public void addFunction(String name, String type, HashMap<String, Double> constants, List<Double> domain) {
        workspace.addFunction(new Function(type, constants, domain), name);
        funcListPanel.addNewFuncLabel(name, type, domain, constants);
        canvasPanel.repaint();
    }

    // REQUIRES: "name" Function object exists in FunctionListPanel.funcLabels
    // MODIFIES: this
    // EFFECTS: removes function from workspace and funcListPanel
    public void removeFunction(String name) {
        workspace.removeFunction(name);
        funcListPanel.removeFuncLabel(name);
        canvasPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: saves the current workspace to file
    public void saveWorkspace() {
        fileHandler.saveFile(workspace);
    }

    // MODIFIES: this
    // EFFECTS: loads a workspace from file
    public void loadWorkspace() {
        int option = fileHandler.loadFile(workspace);
        HashMap<String, Function> functions = workspace.getFunctionList();

        if (option == JFileChooser.APPROVE_OPTION) {
            for (String funcName : functions.keySet()) {
                Function func = functions.get(funcName);
                funcListPanel.addNewFuncLabel(funcName, func);
            }
            canvasPanel.repaint();
        }
    }

    // EFFECTS: returns list of function names in workspace
    public String[] getFunctionNames() {
        HashMap<String, Function> functions = workspace.getFunctionList();
        List<String> funcNames = new ArrayList<>();

        for (String name : functions.keySet()) {
            funcNames.add(name);
        }
        return funcNames.toArray(new String[] {});
    }
}
