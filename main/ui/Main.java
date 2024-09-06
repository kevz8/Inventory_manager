package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            new InventoryApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: File not Found");
        }
    }
}