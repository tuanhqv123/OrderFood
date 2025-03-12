package com.restaurant.orderfood.repository;

import com.restaurant.orderfood.model.Customer;
import com.restaurant.orderfood.model.OrderStatus;
import com.restaurant.orderfood.model.RestaurantOrder;
import com.restaurant.orderfood.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Integer> {
    List<RestaurantOrder> findByStatus(OrderStatus status);

    List<RestaurantOrder> findByTable(RestaurantTable table);

    List<RestaurantOrder> findByTableAndStatus(RestaurantTable table, OrderStatus status);

    List<RestaurantOrder> findByCustomer(Customer customer);

    List<RestaurantOrder> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Optional<RestaurantOrder> findByTableAndStatusNot(RestaurantTable table, OrderStatus status);
}