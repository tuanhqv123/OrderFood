package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.MenuItemStatus;

import java.util.List;

public interface MenuService {
    MenuItem getMenuItemById(Integer id);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByCategoryId(Integer categoryId);

    List<MenuItem> getAvailableMenuItems();

    List<MenuItem> getAvailableMenuItemsByCategoryId(Integer categoryId);

    MenuItem createMenuItem(MenuItem menuItem);

    MenuItem updateMenuItem(Integer id, MenuItem menuItem);

    MenuItem updateMenuItemStatus(Integer id, MenuItemStatus status);

    void deleteMenuItem(Integer id);
}