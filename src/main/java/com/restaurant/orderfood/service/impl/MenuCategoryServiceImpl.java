package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.MenuCategory;
import com.restaurant.orderfood.repository.MenuCategoryRepository;
import com.restaurant.orderfood.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public MenuCategory getCategoryById(Integer id) {
        return menuCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<MenuCategory> getAllCategories() {
        return menuCategoryRepository.findAll();
    }

    @Override
    public MenuCategory createCategory(String name, String description) {
        MenuCategory category = new MenuCategory();
        category.setName(name);
        category.setDescription(description);
        return menuCategoryRepository.save(category);
    }

    @Override
    public MenuCategory updateCategory(Integer id, String name, String description) {
        MenuCategory category = getCategoryById(id);
        if (category != null) {
            category.setName(name);
            category.setDescription(description);
            return menuCategoryRepository.save(category);
        }
        return null;
    }

    @Override
    public void deleteCategory(Integer id) {
        menuCategoryRepository.deleteById(id);
    }
}