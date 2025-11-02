package com.sweetshop.ui;

import com.sweetshop.model.Sale;
import com.sweetshop.model.User;
import com.sweetshop.service.ReportService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportFrame extends JFrame {

    private ReportService reportService;
    private JTable table;
    private DefaultTableModel model;
    private JLabel totalSalesLabel;
    private User user;

    public ReportFrame(User user) {
        this.user = user;
        reportService = new ReportService();

        setTitle("📈 Sales Report - Sweet Shop");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230,230,250));

        JLabel header = new JLabel("Sales Report", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(40,40,80));
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Sale ID", "User ID", "Date", "Total (₹)"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 240, 245));
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(238,238,252));

        JButton refreshBtn = createButton("Refresh");
        JButton backBtn = createButton("← Back to Dashboard");
        totalSalesLabel = new JLabel("Total Sales: ₹0.00", SwingConstants.CENTER);
        totalSalesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalSalesLabel.setForeground(new Color(178, 34, 34));

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(new Color(255, 239, 213));
        btnPanel.add(refreshBtn);
        btnPanel.add(backBtn);

        bottomPanel.add(btnPanel, BorderLayout.NORTH);
        bottomPanel.add(totalSalesLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener((ActionEvent e) -> refreshReport());
        backBtn.addActionListener((ActionEvent e) -> {
            new DashboardFrame(user).setVisible(true);
            dispose();
        });

        refreshReport();
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

    private void refreshReport() {
        model.setRowCount(0);
        List<Sale> sales = reportService.getSalesReport();
        double total = 0;

        for (Sale sale : sales) {
            model.addRow(new Object[]{
                    sale.getSaleId(),
                    sale.getUserId(),
                    sale.getDate(),
                    sale.getTotalAmount()
            });
            total += sale.getTotalAmount();
        }
        totalSalesLabel.setText("Total Sales: ₹" + String.format("%.2f", total));
    }
}
