package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.repository.MenuItemRepository;
import com.restaurant.orderfood.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItem getMenuItemById(Integer id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category);
    }

    @Override
    public List<MenuItem> getMenuItemsByStatus(MenuItem.MenuItemStatus status) {
        return menuItemRepository.findByStatus(status);
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryAndStatus(String category, MenuItem.MenuItemStatus status) {
        return menuItemRepository.findByCategoryAndStatus(category, status);
    }

    @Override
    public MenuItem createMenuItem(String name, BigDecimal price, String category) {
        return createMenuItem(name, price, category, null);
    }

    @Override
    public MenuItem createMenuItem(String name, BigDecimal price, String category, String imageUrl) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setCategory(category);
        menuItem.setImageUrl(imageUrl);
        menuItem.setStatus(MenuItem.MenuItemStatus.AVAILABLE);

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Integer id, String name, BigDecimal price, String category) {
        return updateMenuItem(id, name, price, category, null);
    }

    @Override
    public MenuItem updateMenuItem(Integer id, String name, BigDecimal price, String category, String imageUrl) {
        MenuItem menuItem = getMenuItemById(id);

        if (menuItem == null) {
            throw new IllegalArgumentException("Không tìm thấy món ăn với ID: " + id);
        }

        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setCategory(category);

        if (imageUrl != null) {
            menuItem.setImageUrl(imageUrl);
        }

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItemStatus(Integer id, MenuItem.MenuItemStatus status) {
        MenuItem menuItem = getMenuItemById(id);

        if (menuItem == null) {
            throw new IllegalArgumentException("Không tìm thấy món ăn với ID: " + id);
        }

        menuItem.setStatus(status);

        return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(Integer id) {
        menuItemRepository.deleteById(id);
    }
}