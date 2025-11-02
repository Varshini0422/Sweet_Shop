package com.sweetshop;

import com.sweetshop.util.DBConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {

    @Test
    public void testDatabaseConnection() {
        Connection conn = DBConnection.getConnection();
        assertNotNull(conn, "✅ Database connection should not be null");
    }
}
