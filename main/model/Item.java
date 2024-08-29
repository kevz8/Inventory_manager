package model;

import java.time.LocalDate;

// A class representing an item with a name, quantity, capacity, restock threshold, and last restock date
public class Item {
    private String name;
    private int quantity;
    private int capacity;
    private int restockThreshold;
    private String lastRestockDate;

    // EFFECTS: Constructs an object given the item name, quantity, capacity, and restock threshold
    public Item(String name, int quantity, int capacity, int threshold) {
        this.name = name;
        this.quantity = quantity;
        this.capacity = capacity;
        this.restockThreshold = threshold;
        String currentDate = LocalDate.now().toString();
        this.lastRestockDate = currentDate;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRestockThreshold() {
        return restockThreshold;
    }

    public String getLastRestockdate() {
        return lastRestockDate;
    }

    public void setName(String newname) {
        this.name = newname;
    }

    // REQUIRES: newquantity >= 0
    public void setQuantity(int newquantity) {
        this.quantity = newquantity;
    }

    // REQUIRES: newcapacity >= 0
    public void setCapacity(int newcapacity) {
        this.capacity = newcapacity;
    }

    // REQUIRES: newthreshold >= 0
    public void setRestockTheshold(int newthreshold) {
        this.restockThreshold = newthreshold;
    }

    public void setLastRestockDate(String date) {
        this.lastRestockDate = date;
    }

    // MODIFIES: this
    // EFFECTS: Increases item quantity by one
    public void addOne() {
        this.quantity++;
    }

    // MODIFIES: this
    // EFFECTS: If current item quantity > 0, subtracts the quantity by one; Else, does nothing.
    public void subtractOne() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    // EFFECTS: Gets the current status of the item based on the quantity, capacity, and restock threshold
    public String getStatus() {
        if (this.quantity == 0) {
            return "Empty";
        } else if (this.quantity <= this.restockThreshold) {
            return "Restock Needed";
        } else if (this.quantity > this.capacity) {
            return "Overcapacity";
        } else if (this.quantity == this.capacity) {
            return "Full";
        } else {
            return "Sufficient";
        }
    }

    // EFFECTS: Returns the ratio of the item quantity and capacity
    public String filledCapacity() {
        return this.quantity + "/" + this.capacity;
    }

    // REQUIRES: amount >= 0
    // MODIFES: this
    // EFFECTS: Adds amount to item quantity and takes the current local date 
    public void restock(int amount) {
        this.quantity += amount;
        String currentDate = LocalDate.now().toString();
        this.lastRestockDate = currentDate;
    }
}
