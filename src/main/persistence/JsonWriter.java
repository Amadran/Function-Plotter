package persistence;

import model.Workspace;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes the JSON representation of a workspace to file
// CITATION: modelled after JsonSerializationDemo's JsonWriter class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d79763d7ed5bb61196c51570598336948efe1202
public class JsonWriter {
    private static final int TAB_SIZE = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workspace to file
    public void write(Workspace workspace) {
        JSONObject json = workspace.toJson();
        writer.print(json.toString(TAB_SIZE));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}

