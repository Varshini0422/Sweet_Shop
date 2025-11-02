package com.sweetshop.service;

import com.sweetshop.dao.UserDAO;
import com.sweetshop.model.User;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    // ✅ For login
    public User login(String username, String password) {
        return userDAO.validateUser(username, password);
    }

    // ✅ For registration (new)
    public boolean register(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return userDAO.registerUser(user);
    }
}
