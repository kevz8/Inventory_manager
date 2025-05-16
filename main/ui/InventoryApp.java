package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

import model.*;
import persistence.*;

// Disclaimer: This GUI design was inspired by and based on the DrawingPlayer. 
// Youtube videos and forums were also utilized to help develop the GUI design.
// Source: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter


// An inventory application that allows the user to view the inventory, restock,
// update the inventory, or generate an inventory report
public class InventoryApp extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/inventory.json";
    private Inventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel mainPanel;
    private JPanel mainDisplay;
    private CardLayout switchPanel;
    private JPanel invPanel;
    private JPanel updatePanel;
    private JPanel restockPanel;
    private JPanel reportPanel;
    private JPanel defaultPanel;
    private JLabel loadPic;

    // EFFECTS: Creates an instance of the InventoryApp and runs the application
    public InventoryApp() throws FileNotFoundException {
        runInventory();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input
    private void runInventory() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: Initializes inventory, scanner, and saving/loading capability
    private void init() {
        inventory = new Inventory();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainPanel = new JPanel();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Opens inventory and displays items if not empty
    private void openInventory() {
        reset();
        switchPanel.show(mainDisplay, "Inventory");

        if (inventory.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Items in Inventory");
        } else {
            JPanel labels = new JPanel();
            labels.setLayout(new GridLayout(1, 9));
            labels.add(new JLabel("#"));
            labels.add(new JLabel("Item"));
            labels.add(new JLabel("Quantity"));
            labels.add(new JLabel("Capacity"));
            labels.add(new JLabel("       Add / Substract"));

            invPanel.add(labels);

            displayInventory();
            invPanel.revalidate();
        }
    }

    // EFFECTS: removes displayed graphics
    private void reset() {
        invPanel.removeAll();
        updatePanel.removeAll();
        restockPanel.removeAll();
        reportPanel.removeAll();
        defaultPanel.removeAll();
        loadPic.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: Displays the inventory items, item fields, and add/subtract buttons
    private void displayInventory() {
        int num = 1;
        for (Item item : inventory.getInventory()) {
            JPanel itemInfo = new JPanel(new GridLayout(1, 0));
            itemInfo.add(new JLabel(Integer.toString(num)));
            itemInfo.add(new JLabel(item.getName()));
            JLabel quantityDisplay = new JLabel(Integer.toString(item.getQuantity()));
            itemInfo.add(quantityDisplay);
            itemInfo.add(new JLabel(Integer.toString((item.getCapacity()))));
            viewInventoryButtons(item, itemInfo, quantityDisplay);

            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates buttons to add or subtract one from quantity
    private void viewInventoryButtons(Item item, JPanel itemInfo, JLabel quantityDisplay) {
        JPanel buttons = new JPanel(new GridLayout(1, 0));
        JButton plusOne = new JButton("+");
        plusOne.addActionListener(a -> {
            item.addOne();
            openInventory();
        });
        plusOne.setPreferredSize(new Dimension(10, 50));
        JButton minusOne = new JButton("-");
        minusOne.addActionListener(s -> {
            item.subtractOne();
            openInventory();
        });
        minusOne.setPreferredSize(new Dimension(10, 50));
        buttons.add(plusOne);
        buttons.add(minusOne);
        itemInfo.add(buttons);
        invPanel.add(itemInfo);
    }

    // MODIFIES: this
    // EFFECTS: If inventory isn't empty, displays items in inventory and ability to
    // restock
    private void restockItems() {
        reset();
        switchPanel.show(mainDisplay, "Restock");

        if (inventory.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Items in Inventory");
        } else {
            JPanel labels = new JPanel();
            labels.setLayout(new GridLayout(1, 9));
            labels.add(new JLabel("#"));
            labels.add(new JLabel("Item"));
            labels.add(new JLabel("Quantity"));
            labels.add(new JLabel("   Restock Amount"));
            labels.add(new JLabel(""));

            restockPanel.add(labels);
            displayRestock();
        }

        restockPanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Displays the restock panel item information
    private void displayRestock() {
        int num = 1;

        for (Item item : inventory.getInventory()) {
            JPanel itemInfo = new JPanel(new GridLayout(1, 7));
            itemInfo.add(new JLabel(Integer.toString(num)));
            itemInfo.add(new JLabel(item.getName()));
            itemInfo.add(new JLabel(Integer.toString(item.getQuantity())));

            JTextField restockQuantity = new JTextField(2);
            itemInfo.add(restockQuantity);

            JButton button = new JButton("Save");
            button.setPreferredSize(new Dimension(1, 15));
            button.addActionListener(a -> {
                item.restock(Integer.parseInt(restockQuantity.getText()));
                restockItems();
            });
            itemInfo.add(button);
            restockPanel.add(itemInfo);

            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays options to edit or update current inventory
    private void updateInventory() {
        reset();
        switchPanel.show(mainDisplay, "Update");
        updateInventoryMenu();
        updatePanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Displays menu for updating inventory
    private void updateInventoryMenu() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(a -> addInventoryItem());
        addItemButton.setPreferredSize(new Dimension(100, 30));
        JButton removeItemButton = new JButton("Remove Item");
        removeItemButton.addActionListener(r -> removeInventoryItem());
        removeItemButton.setPreferredSize(new Dimension(100, 30));
        buttons.add(addItemButton);
        buttons.add(removeItemButton);
        updatePanel.add(buttons);

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(1, 9));
        labels.add(new JLabel("#"));
        labels.add(new JLabel("Item"));
        labels.add(new JLabel("Quantity"));
        labels.add(new JLabel("Capacity"));
        labels.add(new JLabel("Threshold"));
        labels.add(new JLabel(""));

        updatePanel.add(labels);

        updateInventoryDisplay();
    }

    // MODIFIES: this
    // EFFECTS: Displays the inventory items and buttons for updating items
    private void updateInventoryDisplay() {
        int num = 1;

        for (Item item : inventory.getInventory()) {
            JPanel itemInfo = new JPanel(new GridLayout(1, 0));
            itemInfo.add(new JLabel(Integer.toString(num)));
            itemInfo.add(new JLabel(item.getName()));
            itemInfo.add(new JLabel(Integer.toString(item.getQuantity())));
            itemInfo.add(new JLabel(Integer.toString(item.getCapacity())));
            itemInfo.add(new JLabel(Integer.toString(item.getRestockThreshold())));

            JButton button = new JButton("Update");
            button.setPreferredSize(new Dimension(10, 30));
            button.addActionListener(a -> updateItem(item));
            itemInfo.add(button);
            updatePanel.add(itemInfo);

            num++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays the item fields and ability to change item information
    private void updateItem(Item item) {
        JPanel addItems = new JPanel(new GridLayout(0, 2));
        addItems.add(new JLabel("Name"));
        JTextField name = new JTextField(8);
        addItems.add(name);
        addItems.add(new JLabel("Quantity"));
        JTextField quantity = new JTextField(3);
        addItems.add(quantity);
        addItems.add(new JLabel("Capacity"));
        JTextField capacity = new JTextField(3);
        addItems.add(capacity);
        addItems.add(new JLabel("Restock Threshold"));
        JTextField threshold = new JTextField(3);
        addItems.add(threshold);

        int input = JOptionPane.showConfirmDialog(null, addItems, "Item Details", JOptionPane.OK_CANCEL_OPTION);
        if (input == JOptionPane.OK_OPTION) {
            updateConditions(name, quantity, capacity, threshold, item);
            updateInventory();
        }
    }

    // MODIFIES: item
    // EFFECTS: if user input isn't empty and is valid, set item field to user's
    // input
    private void updateConditions(JTextField name, JTextField qty, JTextField cap, JTextField threshold, Item item) {
        if (name.getText().length() != 0) {
            item.setName(name.getText());
        }
        if (qty.getText().length() != 0 && Integer.parseInt(qty.getText()) >= 0) {
            item.setQuantity(Integer.parseInt(qty.getText()));
        }
        if (cap.getText().length() != 0 && Integer.parseInt(cap.getText()) >= 0) {
            item.setCapacity(Integer.parseInt(cap.getText()));
        }
        if (threshold.getText().length() != 0 && Integer.parseInt(threshold.getText()) >= 0) {
            item.setRestockTheshold(Integer.parseInt(threshold.getText()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates an item and adds it to the inventory if item does not
    // currently exist
    private void addInventoryItem() {
        JPanel addItems = new JPanel();
        addItems.setLayout(new GridLayout(0, 2));

        addItems.add(new JLabel("Name"));
        JTextField name = new JTextField(8);
        addItems.add(name);
        addItems.add(new JLabel("Quantity"));
        JTextField quantity = new JTextField(3);
        addItems.add(quantity);
        addItems.add(new JLabel("Capacity"));
        JTextField capacity = new JTextField(3);
        addItems.add(capacity);
        addItems.add(new JLabel("Restock Threshold"));
        JTextField threshold = new JTextField(3);
        addItems.add(threshold);

        int input = JOptionPane.showConfirmDialog(null, addItems, "Item Details", JOptionPane.OK_CANCEL_OPTION);
        if (input == JOptionPane.OK_OPTION) {
            inventory.addItem(new Item(name.getText(), Integer.parseInt(quantity.getText()),
                    Integer.parseInt(capacity.getText()), Integer.parseInt(threshold.getText())));
            updateInventory();
        }
    }

    // MODIFIES: this
    // EFFECTS: If inventory isn't empty, select a valid item number to remove item
    // from inventory
    private void removeInventoryItem() {
        if (inventory.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Items in Inventory");
        } else {
            JPanel addItems = new JPanel();
            addItems.setLayout(new GridLayout(0, 1));

            addItems.add(new JLabel("Enter Item Number"));
            JTextField number = new JTextField(3);
            addItems.add(number);

            int input = JOptionPane.showConfirmDialog(null, addItems, "Remove Item", JOptionPane.OK_CANCEL_OPTION);
            if (input == JOptionPane.OK_OPTION) {
                inventory.removeItem(Integer.parseInt(number.getText()));
                updateInventory();
            }
        }

        updatePanel.revalidate();
        updatePanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Generate a report on the items in inventory
    private void generateReport() {
        reset();
        switchPanel.show(mainDisplay, "Report");

        if (inventory.getInventory().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Items in Inventory");
        } else {
            JPanel labels = new JPanel();
            labels.setLayout(new GridLayout(1, 9));
            labels.add(new JLabel("#"));
            labels.add(new JLabel("Item"));
            labels.add(new JLabel("Status"));
            labels.add(new JLabel("Last Restock Date"));
            labels.add(new JLabel("Filled"));
            reportPanel.add(labels);

            displayReport();
        }

        reportPanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Gets all items in inventory and creates a report on the item stock
    private void displayReport() {
        int num = 1;

        for (Item item : inventory.getInventory()) {
            JPanel itemInfo = new JPanel(new GridLayout(1, 0));
            itemInfo.add(new JLabel(Integer.toString(num)));
            itemInfo.add(new JLabel(item.getName()));
            itemInfo.add(new JLabel(item.getStatus()));
            itemInfo.add(new JLabel(item.getLastRestockdate()));
            itemInfo.add(new JLabel(item.filledCapacity()));
            reportPanel.add(itemInfo);

            num++;
        }
    }

    // EFFECTS: Saves current inventory to a JSON file
    private void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Successfully saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save file");
        }
    }

    // MODIFIES: this
    // EFFECTS: Load previous inventory from JSON file
    private void loadInventory() {
        try {
            inventory = jsonReader.read();
            reset();
            switchPanel.show(mainDisplay, "Load");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load inventory");
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this InventoryApp will operate, and
    // populates the menu buttons to be
    // used to manipulate the inventory
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createMainMenu();
        createInventory();
        add(mainDisplay, BorderLayout.CENTER);
        switchPanel.show(mainDisplay, "Default");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates main menu display
    private void createMainMenu() {
        mainPanel.setLayout(new GridLayout(0, 1));
        mainPanel.setSize(new Dimension(0, 0));
        add(mainPanel, BorderLayout.SOUTH);
        createbuttons();
    }

    // MODIFIES: this
    // EFFECTS: Creates GUI panels after pressing a button from the main menu
    private void createInventory() {
        invPanel = new JPanel(new GridLayout(10, 7));
        JScrollPane scrollPanelInv = new JScrollPane(invPanel);

        updatePanel = new JPanel(new GridLayout(10, 7));
        JScrollPane scrollPanelUpdate = new JScrollPane(updatePanel);

        restockPanel = new JPanel(new GridLayout(10, 7));
        JScrollPane scrollPanelRestock = new JScrollPane(restockPanel);

        reportPanel = new JPanel(new GridLayout(10, 7));
        JScrollPane scrollPanelReport = new JScrollPane(reportPanel);

        defaultPanel = new JPanel();
        // Note: Image acquired from FreePik
        defaultPanel.add(new JLabel(new ImageIcon("./data/inventoryIcon.png")));

        Image scaled = new ImageIcon("./data/success.png").getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        loadPic = new JLabel(new ImageIcon(scaled));

        switchPanel = new CardLayout();
        mainDisplay = new JPanel(switchPanel);

        mainDisplay.add(scrollPanelInv, "Inventory");
        mainDisplay.add(scrollPanelUpdate, "Update");
        mainDisplay.add(scrollPanelRestock, "Restock");
        mainDisplay.add(scrollPanelReport, "Report");
        mainDisplay.add(defaultPanel, "Default");
        mainDisplay.add(loadPic, "Load");
    }

    // MODIFIES: this
    // EFFECTS: Creates main menu buttons
    private void createbuttons() {
        JButton inventoryButton = new JButton("View Inventory");
        inventoryButton.addActionListener(v -> openInventory());
        JButton restockButton = new JButton("Restock");
        restockButton.addActionListener(r -> restockItems());
        JButton updateButton = new JButton("Update Inventory");
        updateButton.addActionListener(u -> updateInventory());
        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(g -> generateReport());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(s -> saveInventory());
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(l -> loadInventory());

        mainPanel.add(inventoryButton);
        mainPanel.add(restockButton);
        mainPanel.add(updateButton);
        mainPanel.add(reportButton);
        mainPanel.add(saveButton);
        mainPanel.add(saveButton);
        mainPanel.add(loadButton);
    }
}
