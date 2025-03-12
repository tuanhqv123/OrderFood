package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.MenuCategory;
import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.MenuItemStatus;
import com.restaurant.orderfood.repository.MenuCategoryRepository;
import com.restaurant.orderfood.repository.MenuItemRepository;
import com.restaurant.orderfood.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public MenuItem getMenuItemById(Integer id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found with id: " + id));
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryId(Integer categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        return menuItemRepository.findByCategory(category);
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        return menuItemRepository.findByStatus(MenuItemStatus.AVAILABLE);
    }

    @Override
    public List<MenuItem> getAvailableMenuItemsByCategoryId(Integer categoryId) {
        MenuCategory category = menuCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        return menuItemRepository.findByCategoryAndStatus(category, MenuItemStatus.AVAILABLE);
    }

    @Override
    @Transactional
    public MenuItem createMenuItem(MenuItem menuItem) {
        if (menuItem.getCategory() != null && menuItem.getCategory().getId() != null) {
            MenuCategory category = menuCategoryRepository.findById(menuItem.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found with id: " + menuItem.getCategory().getId()));
            menuItem.setCategory(category);
        }
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public MenuItem updateMenuItem(Integer id, MenuItem menuItemDetails) {
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());

        if (menuItemDetails.getCategory() != null && menuItemDetails.getCategory().getId() != null) {
            MenuCategory category = menuCategoryRepository.findById(menuItemDetails.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found with id: " + menuItemDetails.getCategory().getId()));
            menuItem.setCategory(category);
        }

        menuItem.setStatus(menuItemDetails.getStatus());
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public MenuItem updateMenuItemStatus(Integer id, MenuItemStatus status) {
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setStatus(status);
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public void deleteMenuItem(Integer id) {
        menuItemRepository.deleteById(id);
    }
}