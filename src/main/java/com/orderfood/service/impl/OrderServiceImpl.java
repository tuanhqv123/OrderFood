package com.orderfood.service.impl;

import com.orderfood.model.Cart;
import com.orderfood.model.Order;
import com.orderfood.model.OrderItem;
import com.orderfood.service.CartService;
import com.orderfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong orderIdSequence = new AtomicLong(1);
    private final AtomicLong orderItemIdSequence = new AtomicLong(1);

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(Long tableId) {
        Cart cart = cartService.getCartByTableId(tableId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order with empty cart");
        }

        Long orderId = orderIdSequence.getAndIncrement();
        Order order = new Order(orderId, tableId);

        // Convert cart items to order items
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(
                        orderItemIdSequence.getAndIncrement(),
                        cartItem.getMenuItem(),
                        cartItem.getQuantity(),
                        null))
                .collect(Collectors.toList());

        order.setItems(orderItems);
        order.calculateTotal();
        orders.put(orderId, order);

        // Clear the cart after creating order
        cart.getItems().clear();
        cart.setTotal(java.math.BigDecimal.ZERO);

        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orders.get(id);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        if (status == null) {
            return new ArrayList<>(orders.values());
        }
        return orders.values().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order order = orders.get(id);
        if (order != null) {
            order.setStatus(status);
        }
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orders.remove(id);
    }
}