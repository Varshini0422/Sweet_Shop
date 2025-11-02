package com.sweetshop.model;

public class SaleItem {
    private int saleItemId;
    private int saleId;
    private int itemId;
    private int quantity;
    private double subtotal;

    public SaleItem() {}

    public SaleItem(int saleItemId, int saleId, int itemId, int quantity, double subtotal) {
        this.saleItemId = saleItemId;
        this.saleId = saleId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getSaleItemId() { return saleItemId; }
    public void setSaleItemId(int saleItemId) { this.saleItemId = saleItemId; }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "Item ID: " + itemId + ", Qty: " + quantity + ", Subtotal: ₹" + subtotal;
    }
}
