package com.sweetshop.ui;

import com.sweetshop.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardFrame extends JFrame {

    private User user;

    public DashboardFrame(User user) {
        this.user = user;

        setTitle("🍬 Sweet Shop Dashboard");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(new Color(230, 230, 250));

        // Header
        JLabel header = new JLabel("Sweet Shop Management System", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(40,40,80));
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(40,40,80));
        add(welcomeLabel, BorderLayout.SOUTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton billingBtn = createButton("Billing 💵");
        JButton inventoryBtn = createButton("Inventory 📦");
        JButton reportBtn = createButton("Reports 📊");
        JButton customerBtn = createButton("Customers 👥");
        JButton logoutBtn = createButton("Logout 🚪");
        JButton exitBtn = createButton("Exit ❌");

        buttonPanel.add(billingBtn);
        buttonPanel.add(inventoryBtn);
        buttonPanel.add(reportBtn);
        buttonPanel.add(customerBtn);
        buttonPanel.add(logoutBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // Button Actions
        billingBtn.addActionListener((ActionEvent e) -> new BillingFrame(user).setVisible(true));
        inventoryBtn.addActionListener((ActionEvent e) -> new InventoryFrame(user).setVisible(true));
        reportBtn.addActionListener((ActionEvent e) -> new ReportFrame(user).setVisible(true));
        customerBtn.addActionListener((ActionEvent e) -> new CustomerFrame(user).setVisible(true));

        logoutBtn.addActionListener((ActionEvent e) -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(255,228,225));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setToolTipText("Open " + text);
        return btn;
    }
}
