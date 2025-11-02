package com.sweetshop.ui;

import com.sweetshop.model.*;
import com.sweetshop.service.*;
import com.sweetshop.util.PDFGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class BillingFrame extends JFrame {

    private User user;
    private BillingService billingService;
    private InventoryService inventoryService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private List<SaleItem> saleItems;

    public BillingFrame(User user) {
        this.user = user;
        this.billingService = new BillingService();
        this.inventoryService = new InventoryService();
        this.saleItems = new ArrayList<>();

        setTitle("🍬 Billing - Sweet Shop");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Background Color
        getContentPane().setBackground(new Color(230,230,250));

        JLabel header = new JLabel("Sweet Shop Billing", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(40,40,80));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Item ID", "Name", "Qty", "Price", "Subtotal"}, 0);
        table = new JTable(tableModel);
        table.setBackground(new Color(255, 240, 245));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(238,238,252));
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(255, 239, 213));

        JTextField itemIdField = new JTextField(6);
        JTextField qtyField = new JTextField(6);

        JButton addBtn = createButton("Add Item");
        JButton saveBtn = createButton("Complete Sale");
        JButton backBtn = createButton("← Back to Dashboard");

        inputPanel.add(new JLabel("Item ID:"));
        inputPanel.add(itemIdField);
        inputPanel.add(new JLabel("Qty:"));
        inputPanel.add(qtyField);
        inputPanel.add(addBtn);
        inputPanel.add(saveBtn);
        inputPanel.add(backBtn);

        totalLabel = new JLabel("Total: ₹0.00", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setForeground(new Color(178, 34, 34));

        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        addBtn.addActionListener((ActionEvent e) -> {
            try {
                int itemId = Integer.parseInt(itemIdField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                addItemToBill(itemId, qty);
                itemIdField.setText("");
                qtyField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numbers.");
            }
        });

        saveBtn.addActionListener((ActionEvent e) -> completeSale());

        backBtn.addActionListener((ActionEvent e) -> {
            new DashboardFrame(user).setVisible(true);
            dispose();
        });
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

    private void addItemToBill(int itemId, int qty) {
        SweetItem item = inventoryService.getAllItems()
                .stream()
                .filter(i -> i.getItemId() == itemId)
                .findFirst()
                .orElse(null);

        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found!");
            return;
        }

        if (qty > item.getQuantity()) {
            JOptionPane.showMessageDialog(this, "Not enough stock!");
            return;
        }

        double subtotal = qty * item.getPrice();
        saleItems.add(new SaleItem(0, 0, itemId, qty, subtotal));
        tableModel.addRow(new Object[]{itemId, item.getName(), qty, item.getPrice(), subtotal});
        updateTotal();
    }

    private void updateTotal() {
        double total = billingService.calculateTotal(saleItems);
        totalLabel.setText("Total: ₹" + String.format("%.2f", total));
    }

    private void completeSale() {
        if (saleItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items added!");
            return;
        }

        double total = billingService.calculateTotal(saleItems);
        Sale sale = billingService.createSale(user.getUserId(), total);
        boolean success = billingService.processSale(sale, saleItems);

        if (success) {
            JOptionPane.showMessageDialog(this, "Sale completed successfully!");

            // === Generate PDF Invoice ===
            try {
                PDFGenerator.generateInvoice(sale, saleItems);
                JOptionPane.showMessageDialog(this, "Invoice generated successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to generate invoice!\n" + e.getMessage());
                e.printStackTrace();
            }

            tableModel.setRowCount(0);
            saleItems.clear();
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Sale failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
