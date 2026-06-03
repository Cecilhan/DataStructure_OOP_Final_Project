package DataStructure_OOP_Final_Project.src.exception;

/** Thrown when a phone number fails validation. */
public class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String message) { super(message); }
}

