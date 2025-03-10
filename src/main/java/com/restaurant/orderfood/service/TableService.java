package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.RestaurantTable;

import java.util.List;

public interface TableService {
    RestaurantTable getTableById(Integer id);

    RestaurantTable getOrCreateTable(Integer id);

    List<RestaurantTable> getAllTables();

    List<RestaurantTable> getTablesByStatus(RestaurantTable.TableStatus status);

    RestaurantTable updateTableStatus(Integer id, RestaurantTable.TableStatus status);

    void deleteTable(Integer id);
}