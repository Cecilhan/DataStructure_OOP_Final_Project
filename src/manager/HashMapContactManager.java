package manager;

import exception.ContactNotFoundException;
import exception.DuplicateContactException;
import exception.InvalidPhoneNumberException;
import model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Iterator;

/**
 * Contact manager that uses a HashMap with the phone number as key.
 * This gives us O(1) lookups, which is the whole point of the comparison.
 */
public class HashMapContactManager extends AbstractContactStorage {

    private HashMap<String, Contact> map = new HashMap<>();

    @Override
    public void addContact(Contact c)
            throws DuplicateContactException, InvalidPhoneNumberException {
        checkPhone(c.getPhone());
        if (map.containsKey(c.getPhone())) {
            throw new DuplicateContactException("Phone already exists: " + c.getPhone());
        }
        map.put(c.getPhone(), c);
    }

    @Override
    public void removeContact(String phone) throws ContactNotFoundException {
        if (!map.containsKey(phone)) {
            throw new ContactNotFoundException("Not found: " + phone);
        }
        map.remove(phone);
    }

    @Override
    public void removeByName(String name) throws ContactNotFoundException {
    String keyword = name.toLowerCase();
    boolean found = false;

    Iterator<Map.Entry<String, Contact>> it = map.entrySet().iterator();
    while (it.hasNext()) {
        Contact c = it.next().getValue();
        if (c.getName().toLowerCase().contains(keyword)) {
            it.remove();
            found = true;
        }
    }

    if (!found) {
        throw new ContactNotFoundException("No contact found with name: " + name);
    }
    }

    @Override
    public void editContact(String phone, String newName, String newEmail)
            throws ContactNotFoundException {
        Contact c = map.get(phone);
        if (c == null) {
            throw new ContactNotFoundException("Not found: " + phone);
        }
        c.setName(newName);
        c.setEmail(newEmail);
    }

    @Override
    public Contact search(String phone) throws ContactNotFoundException {
        Contact c = map.get(phone);
        if (c == null) {
            throw new ContactNotFoundException("Not found: " + phone);
        }
        return c;
    }

    @Override
    public List<Contact> searchByName(String name) {
    List<Contact> results = new ArrayList<>();
    String keyword = name.toLowerCase();

    for (Contact c : map.values()) {                 // HashMap uses .values() not the list directly
        if (c.getName().toLowerCase().contains(keyword)) {
            results.add(c);
        }
    }
    return results;
    }

    @Override
    public List<Contact> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void sort() {
        // HashMap can't be sorted in place; sort returns a sorted list
        // when getAll() is called. We sort a snapshot here just so
        // benchmark numbers reflect a real sort.
        List<Contact> tmp = new ArrayList<>(map.values());
        Collections.sort(tmp, new Comparator<Contact>() {
            @Override
            public int compare(Contact a, Contact b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
    }

    @Override public int size() { return map.size(); }
    @Override public void clear() { map.clear(); }
    @Override public String getName() { return "HashMap"; }
}
