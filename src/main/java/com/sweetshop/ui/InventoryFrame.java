package com.sweetshop.ui;

import com.sweetshop.model.SweetItem;
import com.sweetshop.model.User;
import com.sweetshop.service.InventoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InventoryFrame extends JFrame {

    private InventoryService inventoryService;
    private JTable table;
    private DefaultTableModel model;
    private User user;

    public InventoryFrame(User user) {
        this.user = user;
        inventoryService = new InventoryService();

        setTitle("🍭 Inventory Management");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230,230,250));

        JLabel header = new JLabel("Sweet Inventory", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(40,40,80));
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Category", "Price", "Qty"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 240, 245));
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(255, 239, 213));

        JTextField nameField = new JTextField(8);
        JTextField categoryField = new JTextField(8);
        JTextField priceField = new JTextField(6);
        JTextField qtyField = new JTextField(6);

        JButton addBtn = createButton("Add Item");
        JButton refreshBtn = createButton("Refresh");
        JButton backBtn = createButton("← Back");

        controlPanel.add(new JLabel("Name:"));
        controlPanel.add(nameField);
        controlPanel.add(new JLabel("Category:"));
        controlPanel.add(categoryField);
        controlPanel.add(new JLabel("Price:"));
        controlPanel.add(priceField);
        controlPanel.add(new JLabel("Qty:"));
        controlPanel.add(qtyField);
        controlPanel.add(addBtn);
        controlPanel.add(refreshBtn);
        controlPanel.add(backBtn);
        add(controlPanel, BorderLayout.SOUTH);

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());

                SweetItem item = new SweetItem(0, name, category, price, qty);
                boolean added = inventoryService.addNewItem(item);
                if (added) {
                    JOptionPane.showMessageDialog(this, "Item added successfully!");
                    refreshTable();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        refreshBtn.addActionListener((ActionEvent e) -> refreshTable());
        backBtn.addActionListener((ActionEvent e) -> {
            new DashboardFrame(user).setVisible(true);
            dispose();
        });

        refreshTable();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(255, 182, 193));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (SweetItem item : inventoryService.getAllItems()) {
            model.addRow(new Object[]{
                    item.getItemId(),
                    item.getName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getQuantity()
            });
        }
    }
}
