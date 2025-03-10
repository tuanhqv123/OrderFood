package com.restaurant.orderfood.repository;

import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.OrderItem;
import com.restaurant.orderfood.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(RestaurantOrder order);

    List<OrderItem> findByMenuItem(MenuItem menuItem);
}