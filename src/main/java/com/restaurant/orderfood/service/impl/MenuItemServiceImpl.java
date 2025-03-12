package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.MenuCategory;
import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.MenuItemStatus;
import com.restaurant.orderfood.repository.MenuCategoryRepository;
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
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public MenuItem getMenuItemById(Integer id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryId(Integer categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
        return menuItemRepository.findByCategory(category);
    }

    @Override
    public List<MenuItem> getMenuItemsByStatus(MenuItemStatus status) {
        return menuItemRepository.findByStatus(status);
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryIdAndStatus(Integer categoryId, MenuItemStatus status) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
        return menuItemRepository.findByCategoryAndStatus(category, status);
    }

    @Override
    public MenuItem createMenuItem(String name, String description, BigDecimal price, Integer categoryId) {
        return createMenuItem(name, description, price, categoryId, null);
    }

    @Override
    public MenuItem createMenuItem(String name, String description, BigDecimal price, Integer categoryId,
            String imageUrl) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setCategory(category);
        menuItem.setImageUrl(imageUrl);
        menuItem.setStatus(MenuItemStatus.AVAILABLE);

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Integer id, String name, String description, BigDecimal price, Integer categoryId) {
        return updateMenuItem(id, name, description, price, categoryId, null);
    }

    @Override
    public MenuItem updateMenuItem(Integer id, String name, String description, BigDecimal price, Integer categoryId,
            String imageUrl) {
        MenuItem menuItem = getMenuItemById(id);
        if (menuItem != null) {
            MenuCategory category = menuCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

            menuItem.setName(name);
            menuItem.setDescription(description);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            if (imageUrl != null) {
                menuItem.setImageUrl(imageUrl);
            }
            return menuItemRepository.save(menuItem);
        }
        return null;
    }

    @Override
    public MenuItem updateMenuItemStatus(Integer id, MenuItemStatus status) {
        MenuItem menuItem = getMenuItemById(id);
        if (menuItem != null) {
            menuItem.setStatus(status);
            return menuItemRepository.save(menuItem);
        }
        return null;
    }

    @Override
    public void deleteMenuItem(Integer id) {
        menuItemRepository.deleteById(id);
    }
}