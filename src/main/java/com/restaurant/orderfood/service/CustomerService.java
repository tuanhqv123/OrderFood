package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer getCustomerById(Integer id);

    Optional<Customer> getCustomerByPhoneNumber(String phoneNumber);

    Customer getOrCreateCustomer(String phoneNumber);

    List<Customer> getAllCustomers();

    Customer updateCustomerPoints(Integer id, Integer points);

    void deleteCustomer(Integer id);
}