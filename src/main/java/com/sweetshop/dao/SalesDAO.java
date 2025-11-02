package com.sweetshop.dao;

import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    public int addSale(Sale sale) {
        String query = "INSERT INTO sales (user_id, date, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, sale.getUserId());
            ps.setString(2, sale.getDate());
            ps.setDouble(3, sale.getTotalAmount());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // newly created sale_id
            }

        } catch (SQLException e) {
            System.out.println("❌ Error adding sale: " + e.getMessage());
        }
        return -1;
    }

    public boolean addSaleItems(List<SaleItem> saleItems, int saleId) {
        String query = "INSERT INTO sale_items (sale_id, item_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            for (SaleItem item : saleItems) {
                ps.setInt(1, saleId);
                ps.setInt(2, item.getItemId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getSubtotal());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error adding sale items: " + e.getMessage());
            return false;
        }
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM sales";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Sale sale = new Sale(
                        rs.getInt("sale_id"),
                        rs.getInt("user_id"),
                        rs.getString("date"),
                        rs.getDouble("total_amount")
                );
                sales.add(sale);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching sales: " + e.getMessage());
        }
        return sales;
    }
}
