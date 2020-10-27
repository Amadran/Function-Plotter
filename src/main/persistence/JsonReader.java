package persistence;

import model.Function;
import model.Workspace;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

// Represents a reader that reads a workspace from JSON data stored in a file
// CITATION: modelled after JsonSerializationDemo's JsonReader class, with slight modifications
// to read() and parseWorkspace() (with accompanying populateWorkspace() and populateConstantsAndDomain())
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d79763d7ed5bb61196c51570598336948efe1202
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Workspace read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkspace(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // REQUIRES: jsonObject is the JSONObject of the whole data file
    // MODIFIES: Workspace
    // EFFECTS: parses workspace from JSON object and returns it
    private Workspace parseWorkspace(JSONObject jsonObject) {
        //String name = jsonObject.getString("name");
        Workspace workspace = new Workspace();
        populateWorkspace(workspace, jsonObject);
        return workspace;
    }

    // REQUIRES: workspace must be empty, workspaceJson is the JSONObject representing the workspace
    // MODIFIES: Workspace
    // EFFECTS: populates the workspace with JSON data stored in workspaceJson
    private void populateWorkspace(Workspace workspace, JSONObject workspaceJson) {
        JSONArray funcArray = workspaceJson.getJSONArray("functionList");
        for (int i = 0; i < funcArray.length(); i++) {
            JSONObject funcJson = funcArray.getJSONObject(i);
            HashMap<String, Double> constants = new HashMap<>();
            ArrayList<Double> domain = new ArrayList<>();

            //populate constants and domain
            populateConstantsAndDomain(funcJson, constants, domain);

            //create Function object
            Function func = new Function(funcJson.getString("type"), constants, domain);

            //add to workspace
            workspace.addFunction(func, funcJson.getString("name"));
        }
    }

    // REQUIRES: funcJson is a JSONObject representing a Function object,
    //           constants is empty, domain is empty
    // EFFECTS: populates constants HashMap and domain ArrayList with values stored in the funcJson JSONObject
    private void populateConstantsAndDomain(JSONObject funcJson, HashMap<String, Double> constants,
                                            ArrayList<Double> domain) {
        JSONObject constantsJson = funcJson.getJSONObject("constants");
        JSONArray domainJson = funcJson.getJSONArray("domain");
        for (String constKey : constantsJson.keySet()) {
            constants.put(constKey, constantsJson.getDouble(constKey));
        }
        domain.add(domainJson.getDouble(0));
        domain.add(domainJson.getDouble(1));
    }
}
