package persistence;

import org.json.JSONArray;

import model.*;
import java.io.*;

// Disclaimer: This JsonWriter class was modelled after the code from JsonSerializationDemo
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a Writer that writes a JSON representation of Inventory to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of Inventory to file
    public void write(Inventory inv) {
        JSONArray jsonArray = inv.toJson();
        saveToFile(jsonArray.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}