-- Schema for Sweet Shop Management System

CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL CHECK(role IN ('admin', 'cashier'))
);

CREATE TABLE IF NOT EXISTS sweet_items (
    item_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    category TEXT,
    price REAL NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS sales (
    sale_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    date TEXT NOT NULL,
    total_amount REAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS sale_items (
    sale_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
    sale_id INTEGER,
    item_id INTEGER,
    quantity INTEGER NOT NULL,
    subtotal REAL NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(sale_id),
    FOREIGN KEY (item_id) REFERENCES sweet_items(item_id)
);

-- Sample initial data
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('cashier', 'cashier123', 'cashier');

INSERT INTO sweet_items (name, category, price, quantity) VALUES
('Ladoo', 'Traditional', 10.0, 100),
('Jalebi', 'Traditional', 15.0, 80),
('Barfi', 'Milk', 20.0, 50);
