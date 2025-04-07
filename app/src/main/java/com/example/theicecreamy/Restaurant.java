package com.example.theicecreamy;

public class Restaurant {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String cuisine;

    public Restaurant() {
        // Default constructor required for Firebase
    }

    public Restaurant(String id, String name, String address, String contact, String cuisine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.cuisine = cuisine;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getCuisine() {
        return cuisine;
    }
}
