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
public class WorkspaceFileHandler extends JFileChooser {
    private FunctionPlotterGUI mainFrame;

    public WorkspaceFileHandler(FunctionPlotterGUI main) {
        mainFrame = main;
        setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public void saveFile(Workspace workspace) {
        JsonWriter writer;
        int val = showSaveDialog(mainFrame);

        if (val == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFile();
            writer = new JsonWriter("./data/" + file.getName());

            try {
                writer.open();
                writer.write(workspace);
                writer.close();
                System.out.println("File saved to './data/" + file.getName() + "'");
            } catch (FileNotFoundException e) {
                System.out.println("File could not be opened for writing\n");
            }
        }
    }

    public void loadFile(Workspace workspace) {
        JsonReader reader;
        Workspace tempWorkspace;
        int val = showOpenDialog(mainFrame);

        if (val == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFile();
            reader = new JsonReader("./data/" + file.getName());

            try {
                tempWorkspace = reader.read();
                System.out.println("File './data/" + file.getName() + ".json' loaded");

                HashMap<String, Function> functions = tempWorkspace.getFunctionList();
                for (String funcName : functions.keySet()) {
                    workspace.addFunction(functions.get(funcName), funcName);
                }
            } catch (IOException e) {
                System.out.println("File could not be opened for reading\nMake sure the file exists");
            }
        }
    }
}
