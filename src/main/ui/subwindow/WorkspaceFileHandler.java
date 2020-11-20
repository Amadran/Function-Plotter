package ui.subwindow;

import model.Function;
import model.Workspace;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.FunctionPlotterGUI;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

// Opens up a JFileChooser dialog window to load or save the workspace to/from file,
// and handles the loading/saving
// ***CITATION: I learned the basics of various Swing components from the YouTube channel "Bro Code"
// https://www.youtube.com/channel/UC4SVo0Ue36XCfOyb5Lh1viQ, as well as from Oracle Java Swing tutorials
// https://docs.oracle.com/javase/tutorial/uiswing/index.html
public class WorkspaceFileHandler extends JFileChooser {
    private FunctionPlotterGUI mainFrame;


    // REQUIRES: main is the main GUI JFrame (FunctionPlotterGUI)
    // EFFECTS: sets mainFrame and other inherited properties
    public WorkspaceFileHandler(FunctionPlotterGUI main) {
        mainFrame = main;
        setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    // REQUIRES: workspace is the active workspace of the main JFrame
    // MODIFIES: this, writer
    // EFFECTS: saves the current workspace to a json file
    public void saveFile(Workspace workspace) {
        JsonWriter writer;
        int option = showSaveDialog(mainFrame);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFile();
            writer = new JsonWriter(file.getAbsolutePath());

            try {
                writer.open();
                writer.write(workspace);
                writer.close();
                System.out.println("File saved to '" + file.getAbsolutePath() + "'");
            } catch (FileNotFoundException e) {
                System.out.println("File could not be opened for writing\n");
            }
        }
    }

    // REQUIRES: workspace is the active workspace of the main JFrame
    // MODIFIES: this, reader, workspace
    // EFFECTS: loads a workspace from a json file into "workspace"
    public int loadFile(Workspace workspace) {
        JsonReader reader;
        Workspace tempWorkspace;
        int option = showOpenDialog(mainFrame);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFile();
            reader = new JsonReader(file.getAbsolutePath());

            try {
                tempWorkspace = reader.read();
                System.out.println("File '" + file.getAbsolutePath() + "' loaded");

                HashMap<String, Function> functions = tempWorkspace.getFunctionList();
                for (String funcName : functions.keySet()) {
                    workspace.addFunction(functions.get(funcName), funcName);
                }
            } catch (IOException e) {
                System.out.println("File could not be opened for reading\nMake sure the file exists");
            }
        }
        return option;
    }
}
