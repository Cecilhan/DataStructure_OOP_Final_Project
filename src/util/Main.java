// Tells Java this file belongs to the "com.cms" package.
// All our files live under com.cms.* so they can find each other.
package util;

import exception.ContactNotFoundException;
import exception.DuplicateContactException;
import exception.InvalidPhoneNumberException;

import manager.ArrayListContactManager;
import manager.ContactManager;
import manager.HashMapContactManager;
import manager.LinkedListContactManager;

import model.Contact;

// Standard Java classes: List for storing contacts,
// Scanner for reading user input from the keyboard.
import java.util.List;
import java.util.Scanner;

/**
 * Main class. This is where the program starts.
 * It shows a menu, reads the user's choice, and calls the right method.
 */
public class Main {

    // The "active" manager. We can change this at runtime when the user
    // picks option 9 (Switch Data Structure). Because the type is the
    // INTERFACE (ContactManager), the same variable can hold any of the
    // three concrete classes — this is polymorphism in action.
    private static ContactManager manager = new ArrayListContactManager();

    // One Scanner shared by every input method below.
    // We read everything from System.in (the keyboard).
    private static Scanner scanner = new Scanner(System.in);

    // Counter for new contact IDs. We start at 1 and add 1 each time
    // a new contact is created so every contact has a unique ID.
    private static int nextId = 1;

    /**
     * Program entry point. Java looks for this method when the program runs.
     */
    public static void main(String[] args) {

        // Print a welcome banner so the user knows what they opened.
        System.out.println("=========================================");
        System.out.println(" Contact Management System");
        System.out.println(" (comparing ArrayList, LinkedList, HashMap)");
        System.out.println("=========================================");

        // running == false will stop the menu loop and end the program.
        boolean running = true;

        // Keep looping until the user picks "Exit".
        while (running) {

            // Show the menu options to the user.
            showMenu();

            // Read the user's number choice (1-10).
            int choice = readInt("Choice: ");

            // try/catch wraps the whole switch so that ANY error (bad
            // input, missing contact, etc.) shows a friendly message
            // instead of crashing the program.
            try {

                // Run the matching action based on the user's choice.
                switch (choice) {
                    case 1: addContact();    break;  // option 1: add
                    case 2: removeContact(); break;  // option 2: remove
                    case 3: editContact();   break;  // option 3: edit
                    case 4: searchContact(); break;  // option 4: search
                    case 5: displayAll();    break;  // option 5: display all
                    case 6: sortContacts();  break;  // option 6: sort
                    case 7: runBenchmark();  break;  // option 7: benchmark
                    case 8: BenchmarkUtility.printComplexityTable(); break;  // big-O table
                    case 9: switchStructure(); break;  // change active data structure
                    case 10: searchByName();  break;  // option 10: search by name
                    case 11: removeByName();  break;  // option 11: remove by name
                    case 12: running = false; break;  // option 12: exit
                    default: System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                // Catch-all so the user sees a clean error message and
                // the menu keeps running instead of crashing.
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Loop ended → say goodbye and close the scanner.
        System.out.println("Goodbye!");
        scanner.close();
    }

    // -------- the menu --------

    /**
     * Prints the list of options every time we loop.
     * Includes the current data structure so the user knows which one is active.
     */
    private static void showMenu() {
        // Blank line for readability.
        System.out.println();
        // Show which data structure is currently active.
        System.out.println("--- Menu (using: " + manager.getName() + ") ---");
        System.out.println(" 1. Add Contact");
        System.out.println(" 2. Remove Contact");
        System.out.println(" 3. Edit Contact");
        System.out.println(" 4. Search Contact (by phone)");
        System.out.println(" 5. Display All Contacts");
        System.out.println(" 6. Sort Alphabetically");
        System.out.println(" 7. Run Benchmark");
        System.out.println(" 8. Show Time Complexity Table");
        System.out.println(" 9. Switch Data Structure");
        System.out.println(" 10. Search by Name");
        System.out.println(" 11. Remove by Name");   // add this
        System.out.println(" 12. Exit");             // bump Exit
        
    }

    // -------- the 9 menu actions --------

    /**
     * Asks the user for each field and creates a new Contact.
     */
    private static void addContact() {
        System.out.println("-- Add Contact --");

        // Read each piece of info from the user. readLine() returns
        // whatever the user types before pressing Enter.
        String name     = readLine("Name:     ");
        String phone    = readLine("Phone:    ");
        String email    = readLine("Email:    ");
        String address  = readLine("Address:  ");
        String category = readLine("Category: ");

        // Build a Contact object with the data and a fresh ID.
        // nextId++ uses the current value, THEN increments it.
        Contact c = new Contact(nextId++, name, phone, email, address, category);

        // Try to add it. The manager will throw if the phone is
        // duplicate or invalid.
        try {
            manager.addContact(c);
            System.out.println("Contact added.");
        } catch (DuplicateContactException | InvalidPhoneNumberException e) {
            // Show the user what went wrong, then return to the menu.
            System.out.println("Cannot add: " + e.getMessage());
        }
    }

    /**
     * Removes a contact by phone number.
     */
    private static void removeContact() {
        // Ask the user which phone number to remove.
        String phone = readLine("Phone to remove: ");

        try {
            // Pass the phone to the active manager.
            manager.removeContact(phone);
            System.out.println("Removed.");
        } catch (ContactNotFoundException e) {
            // Manager couldn't find that phone → tell the user.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Edits an existing contact's name and email.
     */
    private static void editContact() {
        // Phone number identifies the contact (phone is the unique key).
        String phone = readLine("Phone of contact to edit: ");

        // Ask for the new name and email.
        String newName  = readLine("New name:  ");
        String newEmail = readLine("New email: ");

        try {
            // Try to update; manager throws if phone doesn't exist.
            manager.editContact(phone, newName, newEmail);
            System.out.println("Updated.");
        } catch (ContactNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Looks up a contact by phone number and prints it.
     */
    private static void searchContact() {
        // Ask the user for the phone number to look up.
        String phone = readLine("Phone to search: ");

        try {
            // The manager will return the Contact or throw.
            Contact c = manager.search(phone);
            System.out.println("Found: " + c);
        } catch (ContactNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints every contact currently stored.
     */
    private static void displayAll() {
        // Ask the manager for all the contacts.
        List<Contact> all = manager.getAll();

        // If there are no contacts, say so and return.
        if (all.isEmpty()) {
            System.out.println("No contacts yet.");
            return;
        }

        // Otherwise print how many there are, then each contact.
        System.out.println("Total: " + all.size());
        for (Contact c : all) {
            System.out.println(c);  // uses Contact.toString()
        }
    }

    /**
     * Sorts contacts alphabetically by name, then prints them.
     */
    private static void sortContacts() {
        // Tell the manager to sort. The List-based managers sort in
        // place; the HashMap manager just sorts a copy (it can't keep
        // a meaningful order).
        manager.sort();
        System.out.println("Sorted.");
        // Show the (now sorted) list.
        displayAll();
    }

    /**
     * Runs the full benchmark for a size the user picks.
     */
    private static void runBenchmark() {
        // Show the standard sizes from the project spec.
        System.out.println("Pick a size: 100, 1000, 10000, or 100000");
        int size = readInt("Size: ");

        // 100,000 is very slow with the linear data structures because
        // their cost grows as O(n^2). Warn the user before running it.
        if (size >= 100000) {
            System.out.println("Heads up: at this size the test can take");
            System.out.println("several minutes. Press Ctrl+C to cancel.");
        }

        // Hand off to the utility class which does all the timing.
        BenchmarkUtility.runBenchmark(size);
    }

    /**
     * Lets the user pick which data structure to use as the active manager.
     */
    private static void switchStructure() {
        System.out.println("Pick a data structure:");
        System.out.println(" 1. ArrayList");
        System.out.println(" 2. LinkedList");
        System.out.println(" 3. HashMap");
        int pick = readInt("Choice: ");

        // Replace the active manager based on the choice.
        // This is polymorphism: the variable type doesn't change,
        // only the concrete class behind it.
        switch (pick) {
            case 1: manager = new ArrayListContactManager();  break;
            case 2: manager = new LinkedListContactManager(); break;
            case 3: manager = new HashMapContactManager();    break;
            default:
                System.out.println("Invalid choice. Keeping " + manager.getName() + ".");
                return;
        }

        // Reset the ID counter since the new manager starts empty.
        nextId = 1;
        System.out.println("Now using: " + manager.getName());
    }

    // -------- input helpers --------

    /**
     * Prints the prompt, reads one line of text from the user, returns it.
     */
    private static String readLine(String prompt) {
        System.out.print(prompt);          // show prompt (no newline)
        return scanner.nextLine().trim();   // read line, strip spaces
    }

    /**
     * Like readLine but parses the input as a number. Keeps asking
     * until the user types a valid integer.
     */
    private static int readInt(String prompt) {
        // Loop forever until we successfully parse an int.
        while (true) {
            System.out.print(prompt);                   // show prompt
            String line = scanner.nextLine().trim();    // read user input
            try {
                return Integer.parseInt(line);          // try to parse
            } catch (NumberFormatException e) {
                // Not a number; tell the user and loop again.
                System.out.println("Please enter a number.");
            }
        }
    }
    private static void searchByName() {
    String keyword = readLine("Search name: ");       // ask user what to search
    List<Contact> results = manager.searchByName(keyword);  // call the manager

    if (results.isEmpty()) {
        System.out.println("No contacts found.");
        return;
    }

    System.out.println("Found " + results.size() + " contact(s):");
    for (Contact c : results) {
        System.out.println(c);                         // print each match
    }
}

private static void removeByName() {
    String name = readLine("Name to remove: ");    // ask user

    try {
        manager.removeByName(name);
        System.out.println("Contact(s) removed.");
    } catch (ContactNotFoundException e) {
        System.out.println(e.getMessage());
    }
}
}
