package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.MenuItem;

import java.util.List;

public interface MenuService {
    MenuItem getMenuItemById(Integer id);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByCategory(String category);

    List<MenuItem> getAvailableMenuItems();

    List<MenuItem> getAvailableMenuItemsByCategory(String category);

    MenuItem createMenuItem(MenuItem menuItem);

    MenuItem updateMenuItem(Integer id, MenuItem menuItem);

    MenuItem updateMenuItemStatus(Integer id, MenuItem.MenuItemStatus status);

    void deleteMenuItem(Integer id);
}