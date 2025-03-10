package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.MenuItem;
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
    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category);
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        return menuItemRepository.findByStatus(MenuItem.MenuItemStatus.AVAILABLE);
    }

    @Override
    public List<MenuItem> getAvailableMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategoryAndStatus(category, MenuItem.MenuItemStatus.AVAILABLE);
    }

    @Override
    @Transactional
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public MenuItem updateMenuItem(Integer id, MenuItem menuItemDetails) {
        MenuItem menuItem = getMenuItemById(id);
        menuItem.setName(menuItemDetails.getName());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setCategory(menuItemDetails.getCategory());
        menuItem.setStatus(menuItemDetails.getStatus());
        return menuItemRepository.save(menuItem);
    }

    @Override
    @Transactional
    public MenuItem updateMenuItemStatus(Integer id, MenuItem.MenuItemStatus status) {
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