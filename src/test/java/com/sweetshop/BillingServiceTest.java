package com.sweetshop;

import com.sweetshop.model.SweetItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BillingServiceTest {

    @Test
    public void testTotalCalculation() {
        SweetItem sweet = new SweetItem(1, "Ladoo", "Sweets", 50.0, 2);
        double total = sweet.getPrice() * sweet.getQuantity();
        assertEquals(100.0, total, 0.01, "✅ Total should be ₹100 for 2 ladoos at ₹50 each");
    }
}
