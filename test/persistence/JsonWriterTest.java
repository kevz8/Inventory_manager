package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.*;

// Disclaimer: This JsonWriter Test was modelled after the code from JsonSerializationDemo
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Inventory inv = new Inventory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
            
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Inventory inv = new Inventory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            inv = reader.read();
            assertEquals(0, inv.getInventory().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralInventory() {
        try {
            Inventory inv = new Inventory();
            inv.addItem(new Item("testWrite1", 3, 2, 1));
            inv.addItem(new Item("testWrite2", 7, 8, 5));
            assertEquals(2, inv.getInventory().size());
            String testWriteDate = LocalDate.now().toString();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralInventory.json");
            inv = reader.read();
            List<Item> items = inv.getInventory();
            assertEquals(2, items.size());
            checkItem("testWrite1", 3, 2, 1, testWriteDate, items.get(0));
            checkItem("testWrite2", 7, 8, 5, testWriteDate, items.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}