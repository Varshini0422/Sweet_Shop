package com.sweetshop.service;

import com.sweetshop.dao.SalesDAO;
import com.sweetshop.dao.SweetItemDAO;
import com.sweetshop.model.Sale;
import com.sweetshop.model.SweetItem;

import java.util.List;

public class ReportService {

    private SalesDAO salesDAO;
    private SweetItemDAO itemDAO;

    public ReportService() {
        salesDAO = new SalesDAO();
        itemDAO = new SweetItemDAO();
    }

    public List<Sale> getSalesReport() {
        return salesDAO.getAllSales();
    }

    public List<SweetItem> getStockReport() {
        return itemDAO.getAllItems();
    }
}
