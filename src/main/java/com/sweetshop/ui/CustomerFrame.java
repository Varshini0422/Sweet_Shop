package com.sweetshop.ui;

import com.sweetshop.model.Customer;
import com.sweetshop.model.User;
import com.sweetshop.service.CustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CustomerFrame extends JFrame {

    private CustomerService customerService;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, phoneField, emailField;
    private JTextArea addressArea;
    private User user;

    public CustomerFrame(User user) {
        this.user = user;
        this.customerService = new CustomerService();

        setTitle("👥 Customer Management - Sweet Shop");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230,230,250));

        JLabel header = new JLabel("Customer Management", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(40,40,80));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Phone", "Email", "Address"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 240, 245));
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(238,238,252));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addressArea = new JTextArea(3, 15);
        JScrollPane addressScroll = new JScrollPane(addressArea);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressScroll);

        // Buttons
        JButton addBtn = createButton("Add");
        JButton updateBtn = createButton("Update");
        JButton deleteBtn = createButton("Delete");
        JButton backBtn = createButton("← Back");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 248, 220));
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load existing customers
        loadCustomers();

        // Button Actions
        addBtn.addActionListener((ActionEvent e) -> addCustomer());
        updateBtn.addActionListener((ActionEvent e) -> updateCustomer());
        deleteBtn.addActionListener((ActionEvent e) -> deleteCustomer());
        backBtn.addActionListener((ActionEvent e) -> {
            new DashboardFrame(user).setVisible(true);
            dispose();
        });

        // Table Row Click Event
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                nameField.setText(model.getValueAt(row, 1).toString());
                phoneField.setText(model.getValueAt(row, 2).toString());
                emailField.setText(model.getValueAt(row, 3).toString());
                addressArea.setText(model.getValueAt(row, 4).toString());
            }
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

    private void loadCustomers() {
        model.setRowCount(0);
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer c : customers) {
            model.addRow(new Object[]{
                    c.getCustomerId(),
                    c.getName(),
                    c.getPhone(),
                    c.getEmail(),
                    c.getAddress()
            });
        }
    }

    private void addCustomer() {
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!");
            return;
        }
        Customer c = new Customer(0,
                nameField.getText(),
                phoneField.getText(),
                emailField.getText(),
                addressArea.getText());
        if (customerService.addCustomer(c)) {
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            loadCustomers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding customer!");
        }
    }

    private void updateCustomer() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer to update!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        Customer c = new Customer(id,
                nameField.getText(),
                phoneField.getText(),
                emailField.getText(),
                addressArea.getText());
        if (customerService.updateCustomer(c)) {
            JOptionPane.showMessageDialog(this, "Customer updated successfully!");
            loadCustomers();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating customer!");
        }
    }

    private void deleteCustomer() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer to delete!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && customerService.deleteCustomer(id)) {
            JOptionPane.showMessageDialog(this, "Customer deleted!");
            loadCustomers();
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressArea.setText("");
    }
}
