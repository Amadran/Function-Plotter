package model;

import java.util.HashMap;

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

    //EFFECTS: returns the list of functions
    public HashMap<String, Function> getFunctionList() {
        return functionList;
    }

    // EFFECTS: returns length of functionList
    public int getFunctionListLength() {
        return functionList.size();
    }
}
