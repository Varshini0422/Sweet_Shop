package com.sweetshop.dao;

import com.sweetshop.model.SweetItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SweetItemDAO {

    public List<SweetItem> getAllItems() {
        List<SweetItem> items = new ArrayList<>();
        String query = "SELECT * FROM sweet_items";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SweetItem item = new SweetItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching sweet items: " + e.getMessage());
        }
        return items;
    }

    public boolean addItem(SweetItem item) {
        String query = "INSERT INTO sweet_items (name, category, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error adding sweet item: " + e.getMessage());
            return false;
        }
    }

    public boolean updateQuantity(int itemId, int newQty) {
        String query = "UPDATE sweet_items SET quantity = ? WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, newQty);
            ps.setInt(2, itemId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error updating quantity: " + e.getMessage());
            return false;
        }
    }
}
