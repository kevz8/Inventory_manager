package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestItem {
    private Item item;
    private Item item2;
    private String date;

    @BeforeEach
    void runBefore() {
        item = new Item("Test", 5, 5, 2);
        date = LocalDate.now().toString();
        item2 = new Item("Test2", 0, 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Test", item.getName());
        assertEquals(5, item.getQuantity());
        assertEquals(5, item.getCapacity());
        assertEquals(2, item.getRestockThreshold());
        assertEquals("Test2", item2.getName());
        assertEquals(0, item2.getQuantity());
        assertEquals(0, item2.getCapacity());
        assertEquals(0, item2.getRestockThreshold());
        assertEquals(date, item.getLastRestockdate());
    }

    @Test
    void testSetters() {
        item.setName("newname");
        assertEquals("newname", item.getName());
        item.setQuantity(99);
        assertEquals(99, item.getQuantity());
        item.setCapacity(10);
        assertEquals(10, item.getCapacity());
        item.setRestockTheshold(1);
        assertEquals(1, item.getRestockThreshold());
        item.setLastRestockDate("2023-12-01");
        assertEquals("2023-12-01", item.getLastRestockdate());
    }

    @Test
    void testAddOne() {
        item.addOne();
        assertEquals(6, item.getQuantity());
    }

    @Test
    void testSubtractOne() {
        item.subtractOne();
        assertEquals(4, item.getQuantity());
        item.setQuantity(0);
        item.subtractOne();
        assertEquals(0, item.getQuantity());
    }

    @Test
    void testGetStatus() {
        assertEquals("Full", item.getStatus());
        item.setQuantity(0);
        assertEquals("Empty", item.getStatus());
        item.setQuantity(3);
        assertEquals("Sufficient", item.getStatus());
        item.setQuantity(11);
        assertEquals("Overcapacity", item.getStatus());
        item.setQuantity(2);
        assertEquals("Restock Needed", item.getStatus());
        item.setQuantity(1);
        assertEquals("Restock Needed", item.getStatus());
    }

    @Test
    void testFilledCapacity() {
        assertEquals("5/5", item.filledCapacity());
    }

    @Test
    void testRestock() {
        item.restock(6);
        date = LocalDate.now().toString();
        assertEquals(11, item.getQuantity());
        assertEquals(date, item.getLastRestockdate());
    }

}
