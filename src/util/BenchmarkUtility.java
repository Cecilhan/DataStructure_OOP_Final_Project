package util;

import exception.ContactNotFoundException;
import exception.DuplicateContactException;
import exception.InvalidPhoneNumberException;
import manager.ArrayListContactManager;
import manager.ContactManager;
import manager.HashMapContactManager;
import manager.LinkedListContactManager;
import model.Contact;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * All benchmark-related stuff in one file:
 *  - generate random contacts
 *  - time each operation
 *  - print result tables
 *  - print the complexity table
 */
public class BenchmarkUtility {

    private static final String[] NAMES = {
            "John Smith", "Jane Doe", "Alice Brown", "Bob Davis",
            "Carol Wilson", "David Miller", "Eve Taylor", "Frank Lee"
    };
    private static final String[] CATS = { "Family", "Friend", "Work", "Other" };

    /** Build n random contacts with unique phone numbers */
    public static List<Contact> generate(int n) {
        Random rand = new Random(42); // fixed seed for reproducibility
        List<Contact> result = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            String name = NAMES[rand.nextInt(NAMES.length)];
            String phone = String.format("+1%010d", i); // unique
            String email = "user" + i + "@example.com";
            String addr = (rand.nextInt(9999) + 1) + " Main St";
            String cat = CATS[rand.nextInt(CATS.length)];
            result.add(new Contact(i, name, phone, email, addr, cat));
        }
        return result;
    }

    /** Run all 4 operations on all 3 data structures for the given size */
    public static void runBenchmark(int size) {
        System.out.println();
        System.out.println("=========================================");
        System.out.println(" Running benchmark, size = " + size);
        System.out.println("=========================================");

        List<Contact> data = generate(size);

        ContactManager al = new ArrayListContactManager();
        ContactManager ll = new LinkedListContactManager();
        ContactManager hm = new HashMapContactManager();

        long alAdd = timeAdd(al, data);
        long llAdd = timeAdd(ll, data);
        long hmAdd = timeAdd(hm, data);
        printResult("ADD", size, alAdd, llAdd, hmAdd);

        long alSearch = timeSearch(al, data);
        long llSearch = timeSearch(ll, data);
        long hmSearch = timeSearch(hm, data);
        printResult("SEARCH", size, alSearch, llSearch, hmSearch);

        long alMod = timeModify(al, data);
        long llMod = timeModify(ll, data);
        long hmMod = timeModify(hm, data);
        printResult("MODIFY", size, alMod, llMod, hmMod);

        long alRem = timeRemove(al, data);
        long llRem = timeRemove(ll, data);
        long hmRem = timeRemove(hm, data);
        printResult("REMOVE", size, alRem, llRem, hmRem);
    }

    //Timing method

    private static long timeAdd(ContactManager m, List<Contact> data) {
        m.clear();
        long start = System.nanoTime();
        for (Contact c : data) {
            try { m.addContact(c); }
            catch (DuplicateContactException | InvalidPhoneNumberException e) { /* skip */ }
        }
        return System.nanoTime() - start;
    }

    private static long timeSearch(ContactManager m, List<Contact> data) {
        long start = System.nanoTime();
        for (Contact c : data) {
            try { m.search(c.getPhone()); }
            catch (ContactNotFoundException e) { /* skip */ }
        }
        return System.nanoTime() - start;
    }

    private static long timeModify(ContactManager m, List<Contact> data) {
        long start = System.nanoTime();
        for (Contact c : data) {
            try { m.editContact(c.getPhone(), c.getName() + "_X", c.getEmail()); }
            catch (ContactNotFoundException e) { /* skip */ }
        }
        return System.nanoTime() - start;
    }

    private static long timeRemove(ContactManager m, List<Contact> data) {
        long start = System.nanoTime();
        for (Contact c : data) {
            try { m.removeContact(c.getPhone()); }
            catch (ContactNotFoundException e) { /* skip */ }
        }
        return System.nanoTime() - start;
    }

    // pretty printing

    private static void printResult(String op, int size, long al, long ll, long hm) {
        System.out.println();
        System.out.println("Operation:  " + op + "   |   Input size: " + size);
        System.out.println("-----------------------------------------");
        System.out.printf("  ArrayList    %,15d ns%n", al);
        System.out.printf("  LinkedList   %,15d ns%n", ll);
        System.out.printf("  HashMap      %,15d ns%n", hm);

        String winner = "ArrayList";
        long best = al;
        if (ll < best) { winner = "LinkedList"; best = ll; }
        if (hm < best) { winner = "HashMap"; }
        System.out.println("  -> Fastest: " + winner);
    }

    /* The static Big-O comparison table */
    public static void printComplexityTable() {
        System.out.println();
        System.out.println("   TIME COMPLEXITY ANALYSIS");
        System.out.println("===============================================");
        System.out.println();
        System.out.println("Operation | ArrayList | LinkedList | HashMap");
        System.out.println("-----------------------------------------------");
        System.out.println("Add       | O(n)*     | O(n)*      | O(1)");
        System.out.println("Search    | O(n)      | O(n)       | O(1)");
        System.out.println("Remove    | O(n)      | O(n)       | O(1)");
        System.out.println("Modify    | O(n)      | O(n)       | O(1)");
        System.out.println();
        System.out.println("* Add is O(1) for the insert itself, but we");
        System.out.println("  do a linear duplicate check first, so the");
        System.out.println("  whole Add becomes O(n) in practice.");
        System.out.println("* HashMap is 'average' O(1); collisions could");
        System.out.println("  make it O(n) in the worst case.");
        System.out.println();
    }
}
