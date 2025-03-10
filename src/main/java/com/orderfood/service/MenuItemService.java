package com.orderfood.service;

import com.orderfood.model.MenuItem;
import java.util.List;

public interface MenuItemService {
    MenuItem getMenuItemById(Long id);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByCategory(String category);
}