package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {
    private Item item1;
    private Item item2;
    private Item item3;
    private Inventory inventory;

    @BeforeEach
    void runBefore() {
        item1 = new Item("Item1", 2, 3, 1);
        item2 = new Item("Item2", 0, 0, 0);
        item3 = new Item("Item3", 7, 5, 2);
        inventory = new Inventory();
    }

    @Test
    void testConstructor() {
        assertTrue(inventory.getInventory().isEmpty());
        assertEquals(0, inventory.getInventory().size());
    }

    @Test
    void testAddItem() {
        inventory.addItem(item1);
        assertEquals(1, inventory.getInventory().size());
        inventory.addItem(item1);
        assertEquals(1, inventory.getInventory().size());
        inventory.addItem(item2);
        assertEquals(2, inventory.getInventory().size());
        assertTrue(inventory.addItem(item3));
        assertFalse(inventory.addItem(item2));
    }

    @Test
    void testIsUniqueItem() {
        assertTrue(inventory.isUniqueItem(item1));
        inventory.addItem(item2);
        assertFalse(inventory.isUniqueItem(item2));
        inventory.addItem(item1);
        assertFalse(inventory.isUniqueItem(item2));
        assertTrue(inventory.isUniqueItem(item3));
    }

    @Test
    void testRemoveItem() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        inventory.removeItem(2);
        assertEquals(1, inventory.getInventory().size());
        inventory.removeItem(2);
        assertEquals(1, inventory.getInventory().size());
        inventory.removeItem(0);
        assertEquals(1, inventory.getInventory().size());
    }

    @Test
    void testGetInventoryItem() {
        inventory.addItem(item1);
        assertEquals(item1, inventory.getInventoryItem(1));
    }
}
