package com.example.theicecreamy;

public class Order {
    private String name;
    private String item;
    private int quantity;
    private double price;

    public Order(String name, String item, int quantity, double price) {
        this.name = name;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
