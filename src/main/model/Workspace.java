package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

// Represents the "workspace" of the program, where all of the function objects will reside
public class Workspace {
    private HashMap<String, Function> functionList;

    // EFFECTS: initializes a Workspace with an empty list of functions
    public Workspace() {
        functionList = new HashMap<>();
    }

    // REQUIRES: name must not be an empty string
    // MODIFIES: this, Function
    // EFFECTS: adds a Function object to the list of functions with certain name
    public void addFunction(Function func, String name) {
        functionList.put(name, func);
    }

    // REQUIRES: key "name" must exist in functionList
    // MODIFIES: this, Function
    // EFFECTS: removes Function object corresponding to "name" key in functionList
    public void removeFunction(String name) {
        functionList.remove(name);
    }

    // EFFECTS: returns Function object corresponding to key "name" in functionList, or null
    //          if function with name does not exist
    public Function getFunction(String name) {
        if (functionList.containsKey(name)) {
            return functionList.get(name);
        } else {
            return null;
        }
    }

    // EFFECTS: returns the list of functions
    public HashMap<String, Function> getFunctionList() {
        return functionList;
    }

    // EFFECTS: returns length of functionList
    public int getFunctionListLength() {
        return functionList.size();
    }

    // EFFECTS: returns Workspace object as JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray funcArray = new JSONArray();

        for (String funcName : functionList.keySet()) {
            funcArray.put(functionList.get(funcName).toJson(funcName));
        }
        json.put("functionList", funcArray);

        return json;
    }

    @Override
    // EFFECTS: returns true if this and o are equal by each field, false otherwise
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Workspace workspace = (Workspace) o;
            return functionList.equals(workspace.functionList);
        }
    }

    @Override
    // EFFECTS: generates a hash code for this
    public int hashCode() {
        return Objects.hash(functionList);
    }
}
