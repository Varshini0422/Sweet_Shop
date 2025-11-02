package com.sweetshop.service;

import com.sweetshop.dao.SweetItemDAO;
import com.sweetshop.model.SweetItem;
import java.util.List;

public class InventoryService {

    private SweetItemDAO itemDAO;

    public InventoryService() {
        itemDAO = new SweetItemDAO();
    }

    public List<SweetItem> getAllItems() {
        return itemDAO.getAllItems();
    }

    public boolean addNewItem(SweetItem item) {
        return itemDAO.addItem(item);
    }

    public boolean updateStock(int itemId, int newQty) {
        return itemDAO.updateQuantity(itemId, newQty);
    }
}
