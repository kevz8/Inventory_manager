package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.*;

// Disclaimer: This JsonReader class was modelled after the code from JsonSerializationDemo
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads a Inventory from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: Creates a Reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads an Inventory from file and returns it
    public Inventory read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseInventory(jsonArray);
    }

    // EFFECTS: Reads a source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: Creates an Inventory with the parsed items and returns it
    private Inventory parseInventory(JSONArray jsonArray) {
        Inventory inventory = new Inventory();
        addItems(inventory, jsonArray);
        return inventory;
    }

    // MODIFIES: inventory
    // EFFECTS: Parses items from JSONObject and adds it to Inventory
    private void addItems(Inventory inventory, JSONArray jsonArray) {
        for (Object json: jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            String name = nextItem.getString("name");
            int quantity = nextItem.getInt("quantity");
            int capacity = nextItem.getInt("capacity");
            int threshold = nextItem.getInt("restock threshold");
            String lastRestockDate = nextItem.getString("last restock");

            inventory.addItem(new Item(name, quantity, capacity, threshold));
            inventory.getInventory().get(inventory.getInventory().size() - 1).setLastRestockDate(lastRestockDate);
        }
    }
}