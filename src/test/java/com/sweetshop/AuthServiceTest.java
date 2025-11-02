package com.sweetshop;

import com.sweetshop.service.AuthService;
import com.sweetshop.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    public void testLoginWithValidUser() {
        AuthService auth = new AuthService();
        User user = auth.login("admin", "admin123");
        assertNotNull(user, "✅ Login should return a valid User object for correct credentials");
    }

    @Test
    public void testLoginWithInvalidUser() {
        AuthService auth = new AuthService();
        User user = auth.login("wrongUser", "wrongPass");
        assertNull(user, "❌ Login should return null for invalid credentials");
    }
}
