package com.restaurant.orderfood.service;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.dto.OrderDto;
import com.restaurant.orderfood.model.RestaurantOrder;
import com.restaurant.orderfood.model.RestaurantTable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    RestaurantOrder getOrderById(Integer id);

    List<RestaurantOrder> getAllOrders();

    List<RestaurantOrder> getOrdersByStatus(RestaurantOrder.OrderStatus status);

    List<RestaurantOrder> getOrdersByTable(RestaurantTable table);

    List<RestaurantOrder> getOrdersByTableAndStatus(RestaurantTable table, RestaurantOrder.OrderStatus status);

    Optional<RestaurantOrder> getActiveOrderByTable(RestaurantTable table);

    List<RestaurantOrder> getOrdersByDateRange(LocalDateTime start, LocalDateTime end);

    RestaurantOrder createOrder(CartDto cart, String phoneNumber);

    OrderDto createOrderFromCart(Integer tableId);

    CartDto getCartForTable(Integer tableId);

    RestaurantOrder updateOrderStatus(Integer id, RestaurantOrder.OrderStatus status);

    void cancelOrder(Integer id);
}