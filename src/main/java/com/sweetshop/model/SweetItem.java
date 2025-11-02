package com.sweetshop.model;

public class SweetItem {
    private int itemId;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public SweetItem() {}

    public SweetItem(int itemId, String name, String category, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " (" + category + ") - ₹" + price + " x " + quantity;
    }
}
