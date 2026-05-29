package manager;

import exception.InvalidPhoneNumberException;
/**
 * Base class that holds shared logic for the three concrete managers.
 * Right now that's just phone number validation.
 */
public abstract class AbstractContactStorage implements ContactManager {

    /** Phone number must have 8 to 15 digits. */
    protected void checkPhone(String phone) throws InvalidPhoneNumberException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("Phone cannot be empty.");
        }
        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.length() < 8 || digits.length() > 15) {
            throw new InvalidPhoneNumberException("Phone must have 8-15 digits.");
        }
    }
}

