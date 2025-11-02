package com.sweetshop.ui;

import com.sweetshop.model.User;
import com.sweetshop.service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public RegisterFrame() {
        setTitle("Register - Sweet Shop");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(230, 230, 250));

        JLabel title = new JLabel("Register New User", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(40,40,80));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"admin", "cashier"});
        panel.add(roleComboBox);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(39,174,96));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.addActionListener(this::handleRegister);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        panel.add(registerBtn);
        panel.add(backBtn);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void handleRegister(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        AuthService authService = new AuthService();
        boolean success = authService.register(username, password, role);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!");
        }
    }
}
