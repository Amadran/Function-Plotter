package persistence;

import org.json.JSONObject;

// Simple interface which designates a method to convert a class
// object's data into JSON as a string
// CITATION: from persistence package from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d79763d7ed5bb61196c51570598336948efe1202
public interface Writable {
    // EFFECTS: returns this converted as a JSONObject
    JSONObject toJson(String name);
}
