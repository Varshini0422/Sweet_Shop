package com.sweetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int saleId;
    private int userId;
    private String date;
    private double totalAmount;
    private List<SaleItem> items;

    public Sale() {
        items = new ArrayList<>();
    }

    public Sale(int saleId, int userId, String date, double totalAmount) {
        this.saleId = saleId;
        this.userId = userId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>();
    }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<SaleItem> getItems() { return items; }
    public void addItem(SaleItem item) { this.items.add(item); }

    @Override
    public String toString() {
        return "Sale ID: " + saleId + " | Total: ₹" + totalAmount + " | Date: " + date;
    }
}
