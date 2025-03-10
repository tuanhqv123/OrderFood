package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.MenuItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    MenuItem getMenuItemById(Integer id);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByCategory(String category);

    List<MenuItem> getMenuItemsByStatus(MenuItem.MenuItemStatus status);

    List<MenuItem> getMenuItemsByCategoryAndStatus(String category, MenuItem.MenuItemStatus status);

    MenuItem createMenuItem(String name, BigDecimal price, String category);

    MenuItem createMenuItem(String name, BigDecimal price, String category, String imageUrl);

    MenuItem updateMenuItem(Integer id, String name, BigDecimal price, String category);

    MenuItem updateMenuItem(Integer id, String name, BigDecimal price, String category, String imageUrl);

    MenuItem updateMenuItemStatus(Integer id, MenuItem.MenuItemStatus status);

    void deleteMenuItem(Integer id);
}