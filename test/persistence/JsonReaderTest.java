package persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.*;

// Disclaimer: This JsonReader Test was modelled after the code from JsonSerializationDemo
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistentFile.json");
        try {
            Inventory inv = reader.read();
            fail("IOException expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            Inventory inv = reader.read();
            assertEquals(0, inv.getInventory().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralInventory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralInventory.json");
        try {
            Inventory inv = reader.read();
            List<Item> items = inv.getInventory();
            assertEquals(2, items.size());
            checkItem("Item1", 2, 1, 1, "2024-07-22", items.get(0));
            checkItem("Item2", 3, 6, 2, "2024-07-23", items.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}