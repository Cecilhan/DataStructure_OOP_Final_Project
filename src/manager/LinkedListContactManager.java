package manager;

import exception.ContactNotFoundException;
import exception.DuplicateContactException;
import exception.InvalidPhoneNumberException;
import model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Contact manager that uses a LinkedList to store contacts.
 */
public class LinkedListContactManager extends AbstractContactStorage {

    private LinkedList<Contact> list = new LinkedList<>();

    @Override
    public void addContact(Contact c)
            throws DuplicateContactException, InvalidPhoneNumberException {
        checkPhone(c.getPhone());
        for (Contact x : list) {
            if (x.getPhone().equals(c.getPhone())) {
                throw new DuplicateContactException("Phone already exists: " + c.getPhone());
            }
        }
        list.add(c);
    }

    @Override
    public void removeContact(String phone) throws ContactNotFoundException {
        Iterator<Contact> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getPhone().equals(phone)) {
                it.remove();
                return;
            }
        }
        throw new ContactNotFoundException("Not found: " + phone);
    }

    @Override
    public void removeByName(String name) throws ContactNotFoundException {
    String keyword = name.toLowerCase();
    boolean found = false;                              // track if anything was removed

    Iterator<Contact> it = list.iterator();            // use iterator so we can remove safely
    while (it.hasNext()) {
        Contact c = it.next();
        if (c.getName().toLowerCase().contains(keyword)) {
            it.remove();                               // remove this contact
            found = true;                              // mark that we removed at least one
        }
    }

    if (!found) {
        throw new ContactNotFoundException("No contact found with name: " + name);
    }
    }

    @Override
    public void editContact(String phone, String newName, String newEmail)
            throws ContactNotFoundException {
        for (Contact c : list) {
            if (c.getPhone().equals(phone)) {
                c.setName(newName);
                c.setEmail(newEmail);
                return;
            }
        }
        throw new ContactNotFoundException("Not found: " + phone);
    }

    @Override
    public Contact search(String phone) throws ContactNotFoundException {
        for (Contact c : list) {
            if (c.getPhone().equals(phone)) {
                return c;
            }
        }
        throw new ContactNotFoundException("Not found: " + phone);
    }

    @Override
    public List<Contact> searchByName(String name) {
    List<Contact> results = new ArrayList<>();       // empty list to collect matches
    String keyword = name.toLowerCase();             // lowercase so search is case-insensitive

    for (Contact c : list) {                         // loop through every contact
        if (c.getName().toLowerCase().contains(keyword)) {  // partial match check
            results.add(c);                          // add to results if it matches
        }
    }
    return results;                                  // return all matches (could be empty)
    }

    @Override
    public List<Contact> getAll() { return list; }

    @Override
    public void sort() {
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact a, Contact b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
    }

    @Override public int size() { return list.size(); }
    @Override public void clear() { list.clear(); }
    @Override public String getName() { return "LinkedList"; }
}
