package com.orderfood.service.impl;

import com.orderfood.model.MenuItem;
import com.orderfood.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final Map<Long, MenuItem> menuItems = new ConcurrentHashMap<>();

    public MenuItemServiceImpl() {
        // Add some sample menu items
        menuItems.put(1L, new MenuItem(1L, "Phở Bò", "Phở với thịt bò tươi", new BigDecimal("65000"),
                "/images/pho-bo.jpg", "Phở"));
        menuItems.put(2L,
                new MenuItem(2L, "Phở Gà", "Phở với thịt gà", new BigDecimal("60000"), "/images/pho-ga.jpg", "Phở"));
        menuItems.put(3L, new MenuItem(3L, "Cơm Sườn", "Cơm với sườn nướng", new BigDecimal("55000"),
                "/images/com-suon.jpg", "Cơm"));
        menuItems.put(4L,
                new MenuItem(4L, "Cơm Gà", "Cơm với gà rán", new BigDecimal("50000"), "/images/com-ga.jpg", "Cơm"));
        menuItems.put(5L,
                new MenuItem(5L, "Bún Bò Huế", "Bún bò cay Huế", new BigDecimal("70000"), "/images/bun-bo.jpg", "Bún"));
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItems.get(id);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menuItems.values());
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItems.values().stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}