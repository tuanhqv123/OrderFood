package com.restaurant.orderfood.repository;

import com.restaurant.orderfood.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByCategory(String category);

    List<MenuItem> findByStatus(MenuItem.MenuItemStatus status);

    List<MenuItem> findByCategoryAndStatus(String category, MenuItem.MenuItemStatus status);
}