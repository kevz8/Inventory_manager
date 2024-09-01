package persistence;

import static org.junit.Assert.assertEquals;

import model.Item;

public class JsonTest {
    protected void checkItem(String name, int quantity, int capacity, int threshold, String date, Item item) {
        assertEquals(name, item.getName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(capacity, item.getCapacity());
        assertEquals(threshold, item.getRestockThreshold());
        assertEquals(date, item.getLastRestockdate());
    }
}
