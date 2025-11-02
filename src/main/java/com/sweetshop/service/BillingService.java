package com.sweetshop.service;

import com.sweetshop.dao.SalesDAO;
import com.sweetshop.dao.SweetItemDAO;
import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import com.sweetshop.model.SweetItem;

import java.time.LocalDate;
import java.util.List;

public class BillingService {

    private SalesDAO salesDAO;
    private SweetItemDAO itemDAO;

    public BillingService() {
        salesDAO = new SalesDAO();
        itemDAO = new SweetItemDAO();
    }

    public boolean processSale(Sale sale, List<SaleItem> saleItems) {
        try {
            // 1️⃣ Save sale header
            int saleId = salesDAO.addSale(sale);
            if (saleId <= 0) return false;

            // 2️⃣ Save each item line
            boolean itemsAdded = salesDAO.addSaleItems(saleItems, saleId);

            // 3️⃣ Update inventory
            for (SaleItem si : saleItems) {
                SweetItem item = itemDAO.getAllItems()
                        .stream()
                        .filter(i -> i.getItemId() == si.getItemId())
                        .findFirst().orElse(null);
                if (item != null) {
                    int newQty = item.getQuantity() - si.getQuantity();
                    itemDAO.updateQuantity(item.getItemId(), newQty);
                }
            }

            return itemsAdded;
        } catch (Exception e) {
            System.out.println("❌ Billing failed: " + e.getMessage());
            return false;
        }
    }

    public double calculateTotal(List<SaleItem> items) {
        double total = 0;
        for (SaleItem i : items) total += i.getSubtotal();
        return total;
    }

    public Sale createSale(int userId, double totalAmount) {
        String today = LocalDate.now().toString();
        return new Sale(0, userId, today, totalAmount);
    }
}
