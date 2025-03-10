package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.service.CartService;
import com.restaurant.orderfood.service.OrderService;
import com.restaurant.orderfood.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CartService cartService;
    private final OrderService orderService;
    private final Random random = new Random();

    @Override
    @Transactional
    public String processPayment(Integer tableId, String phoneNumber) {
        try {
            // In a real application, this would process the payment through a payment
            // gateway
            // For this demo, we'll just clear the cart and return a fake invoice ID

            // Create an order from the cart if it's not empty
            try {
                orderService.createOrderFromCart(tableId);
            } catch (Exception e) {
                System.err.println("Error creating order during payment: " + e.getMessage());
                // Continue with payment even if order creation fails
            }

            // Clear the cart
            cartService.clearCart(tableId);

            // Generate a fake invoice ID
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String randomSuffix = String.format("%04d", random.nextInt(10000));

            return "INV-" + timestamp + "-" + randomSuffix;
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();

            // Return a dummy invoice ID for development purposes
            return "INV-ERROR-" + System.currentTimeMillis();
        }
    }
}