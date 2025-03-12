package com.restaurant.orderfood.repository;

import com.restaurant.orderfood.model.MenuCategory;
import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.MenuItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByCategory(MenuCategory category);

    List<MenuItem> findByStatus(MenuItemStatus status);

    List<MenuItem> findByCategoryAndStatus(MenuCategory category, MenuItemStatus status);
}