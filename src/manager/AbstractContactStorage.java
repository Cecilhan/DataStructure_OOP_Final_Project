package manager;

import exception.InvalidPhoneNumberException;
public abstract class AbstractContactStorage implements ContactManager {

    //Phone number must have 8 to 15 digits
    protected void checkPhone(String phone) throws InvalidPhoneNumberException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("Phone cannot be empty.");
        }
        // Strip formatting characters (+, -, spaces) so only digits are counted
        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.length() < 8 || digits.length() > 15) {
            throw new InvalidPhoneNumberException("Phone must have 8-15 digits.");
        }
    }
}

