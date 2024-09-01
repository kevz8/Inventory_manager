package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a inventory with a list of items
public class Inventory implements Writable {
    private List<Item> listOfItems;

    // EFFECTS: Constructs an inventory with no items added
    public Inventory() {
        this.listOfItems = new ArrayList<>();
    }

    public List<Item> getInventory() {
        return listOfItems;
    }

    // Modifies: this
    // EFFECTS: Produces true if item is added to inventory; Else, false
    public Boolean addItem(Item i) {
        if (isUniqueItem(i)) {
            this.listOfItems.add(i);
            return true;
        }
        return false;
    }

    // EFFECTS: Produces true if item name is unique in the inventory
    public Boolean isUniqueItem(Item i) {
        for (Item item: this.listOfItems) {
            if (item.getName().equals(i.getName())) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: Given an integer num, removes the item from the inventory if num > 0
    //          AND num <= listOfItems.size()
    public void removeItem(int num) {
        if (num > 0 && num <= listOfItems.size()) {
            listOfItems.remove(num - 1);
        }
    }

    // REQUIRES: A valid item number in the list
    // EFFECTS: Gets the item given the item number
    public Item getInventoryItem(int num) {
        return getInventory().get(num - 1);
    }

    // EFFECTS: Saves current inventory items to jsonArray
    @Override
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : getInventory()) {
            JSONObject json = new JSONObject();
            json.put("name", item.getName());
            json.put("quantity", item.getQuantity());
            json.put("capacity", item.getCapacity());
            json.put("restock threshold", item.getRestockThreshold());
            json.put("last restock", item.getLastRestockdate());
            jsonArray.put(json);
        }

        return jsonArray;
    }
}