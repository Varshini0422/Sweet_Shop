package com.sweetshop.service;

import com.sweetshop.dao.CustomerDAO;
import com.sweetshop.model.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    // Add new customer
    public boolean addCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    // Update existing customer details
    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    // Delete a customer
    public boolean deleteCustomer(int id) {
        return customerDAO.deleteCustomer(id);
    }

    // Fetch all customers
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}
