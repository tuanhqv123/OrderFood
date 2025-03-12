package com.restaurant.orderfood.service;

import com.restaurant.orderfood.model.MenuCategory;
import java.util.List;

public interface MenuCategoryService {
    MenuCategory getCategoryById(Integer id);

    List<MenuCategory> getAllCategories();

    MenuCategory createCategory(String name, String description);

    MenuCategory updateCategory(Integer id, String name, String description);

    void deleteCategory(Integer id);
}