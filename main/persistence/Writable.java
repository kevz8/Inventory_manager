package persistence;

import org.json.JSONArray;

// Disclaimer: This Writable interface was modelled after the code from JsonSerializationDemo
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONArray toJson();
}