package manager;

import model.Contact;
import java.util.List;
import exception.ContactNotFoundException;
import exception.DuplicateContactException;
import exception.InvalidPhoneNumberException;

/**
 * The contract every contact manager must follow.
 * Implemented by three different data structures.
 */
public interface ContactManager {

    void addContact(Contact c)
            throws DuplicateContactException, InvalidPhoneNumberException;

    void removeContact(String phone) throws ContactNotFoundException;

    void removeByName(String name) throws ContactNotFoundException;

    void editContact(String phone, String newName, String newEmail)
            throws ContactNotFoundException;

    Contact search(String phone) throws ContactNotFoundException;

    List<Contact> searchByName(String name);

    List<Contact> getAll();

    void sort();

    int size();

    void clear();

    String getName();   // returns "ArrayList", "LinkedList", or "HashMap"
}
