package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.MenuItemStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    MenuItem getMenuItemById(Integer id);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByCategoryId(Integer categoryId);

    List<MenuItem> getMenuItemsByStatus(MenuItemStatus status);

    List<MenuItem> getMenuItemsByCategoryIdAndStatus(Integer categoryId, MenuItemStatus status);

    MenuItem createMenuItem(String name, String description, BigDecimal price, Integer categoryId);

    MenuItem createMenuItem(String name, String description, BigDecimal price, Integer categoryId, String imageUrl);

    MenuItem updateMenuItem(Integer id, String name, String description, BigDecimal price, Integer categoryId);

    MenuItem updateMenuItem(Integer id, String name, String description, BigDecimal price, Integer categoryId,
            String imageUrl);

    MenuItem updateMenuItemStatus(Integer id, MenuItemStatus status);

    void deleteMenuItem(Integer id);
}