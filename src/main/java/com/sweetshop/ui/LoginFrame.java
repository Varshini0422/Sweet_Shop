package com.sweetshop.ui;

import com.sweetshop.model.User;
import com.sweetshop.service.AuthService;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginFrame() {
        setTitle("🍬 Sweet Shop - Login");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 🎨 Background panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 250)); // pastel cream
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Sweet Shop Login");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40,40,80));
        titleLabel.setBounds(100, 40, 250, 40);
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        userLabel.setBounds(60, 120, 100, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 120, 180, 25);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        passLabel.setBounds(60, 170, 100, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 170, 180, 25);
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Poppins", Font.BOLD, 14));
        loginBtn.setBackground(new Color(39,174,96));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setBounds(140, 220, 120, 35);
        loginBtn.addActionListener(this::handleLogin);
        panel.add(loginBtn);

        // 🧭 Register button
        JButton registerBtn = new JButton("New user? Register here");
        registerBtn.setFont(new Font("Poppins", Font.PLAIN, 12));
        registerBtn.setBackground(new Color(230, 204, 255));
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setBounds(120, 270, 180, 30);
        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
        panel.add(registerBtn);

        // 🔙 Exit button
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Poppins", Font.BOLD, 12));
        exitBtn.setBackground(new Color(192, 57, 43));
        exitBtn.setForeground(Color.BLACK);
        exitBtn.setBounds(160, 310, 100, 30);
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn);

        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Poppins", Font.BOLD, 12));
        statusLabel.setBounds(50, 90, 300, 20);
        panel.add(statusLabel);

        add(panel);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Please enter both username and password!");
            return;
        }

        AuthService authService = new AuthService();
        User user = authService.login(username, password);

        if (user != null) {
            statusLabel.setForeground(new Color(0, 128, 0));
            statusLabel.setText("Login successful!");
            new DashboardFrame(user).setVisible(true);
            dispose();
        } else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Invalid credentials. Try again!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
