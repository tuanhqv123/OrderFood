package com.restaurant.orderfood.repository;

import com.restaurant.orderfood.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {
    List<RestaurantTable> findByStatus(RestaurantTable.TableStatus status);
}