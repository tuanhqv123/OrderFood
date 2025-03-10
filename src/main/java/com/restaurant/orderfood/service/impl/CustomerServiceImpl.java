package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.Customer;
import com.restaurant.orderfood.repository.CustomerRepository;
import com.restaurant.orderfood.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public Customer getOrCreateCustomer(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setPhoneNumber(phoneNumber);
                    newCustomer.setPoints(0);
                    newCustomer.setTier(Customer.CustomerTier.REGULAR);
                    return customerRepository.save(newCustomer);
                });
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer updateCustomerPoints(Integer id, Integer points) {
        Customer customer = getCustomerById(id);
        customer.setPoints(customer.getPoints() + points);

        // Update customer tier based on points
        if (customer.getPoints() >= 1000) {
            customer.setTier(Customer.CustomerTier.GOLD);
        } else if (customer.getPoints() >= 500) {
            customer.setTier(Customer.CustomerTier.SILVER);
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}