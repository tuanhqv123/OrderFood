package com.restaurant.service;

import com.restaurant.model.MenuItem;
import java.util.List;

public interface MenuItemService {
    MenuItem getMenuItem(Long id);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByCategory(String category);

    MenuItem saveMenuItem(MenuItem menuItem);

    void deleteMenuItem(Long id);
}