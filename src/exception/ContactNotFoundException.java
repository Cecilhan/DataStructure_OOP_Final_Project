package DataStructure_OOP_Final_Project.src.exception;

/** Thrown when a contact we're looking for doesn't exist. */
public class ContactNotFoundException extends Exception {
    public ContactNotFoundException(String message) { super(message); }
}

