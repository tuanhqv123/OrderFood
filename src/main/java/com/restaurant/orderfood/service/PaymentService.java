package com.restaurant.orderfood.service;

public interface PaymentService {
    /**
     * Process payment for a table
     * 
     * @param tableId     The ID of the table
     * @param phoneNumber Optional phone number for loyalty points
     * @return The invoice ID
     */
    String processPayment(Integer tableId, String phoneNumber);
}