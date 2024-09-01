# Inventory Manager:
## About the Application:

The Inventory Manager helps users keep track of **inventory items** and **item count**. With this, users can also generate a report on items': 
- Status (whether an item needs restocking, is over-capacity, is full, or is sufficient)
- Last Restock Date
- Filled capacity

This application applies to those working in inventory **management** such as warehouse employees, business owners, lab maintenance workers, or other roles involving the need to keep track of inventory items. The application provides users with an **easy-to-use** system for maintaining stock levels to ensure users have the right number of items available.


# User Stories

Features:
- As a user, I want to be able to add items to my inventory and specify the item name, quantity, capacity, and restock threshold.
- As a user, I want to be able to remove an item from my inventory.
- As a user, I want to be able to view the list of items in my inventory.
- As a user, I want to be able to update an item's name, quantity, capacity, and restock threshold.
- As a user, I want to be able to generate a report with the item status, last restock date, and item capacity filled.
- As a user, I want to be able to save my inventory to a file.
- As a user, I want to be able to load my inventory from a file.


# Future improvements:

Reflecting on my design, I notice that I can improve cohesion by creating another class to manage the inventory functionality. Currently, the Inventory class directly interacts with the Item class and performs all the actions. As a result, I could create a class between the Inventory class and Item class that handles the inventory actions such as adding items, removing items, updating items etc to better follow the single responsibility principle. 

Another potential refactor is the removal of the Writable interface. Since Writable only features one method and Inventory is the only class implementing Writable, the interface may be unnecessary and provide little value (assuming that the application stay the same). This could improve readability as the structure becomes more straightforward with fewer files to check and understand.
