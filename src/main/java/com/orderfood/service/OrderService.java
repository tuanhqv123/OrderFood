package com.orderfood.service;

import com.orderfood.model.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Long tableId);

    Order getOrderById(Long id);

    List<Order> getOrdersByStatus(String status);

    Order updateOrderStatus(Long id, String status);

    void deleteOrder(Long id);
}