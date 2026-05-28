package com.cms.model;
/**
 * Contact - holds the info for one person.
 */
public class Contact {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String category;

    public Contact(int id, String name, String phone,
    String email, String address, String category) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "ID " + id + " | " + name + " | " + phone
                + " | " + email + " | " + address + " | " + category;
    }
}
