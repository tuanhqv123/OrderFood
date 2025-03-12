package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.MenuItemStatus;
import com.restaurant.orderfood.service.MenuCategoryService;
import com.restaurant.orderfood.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/menu-management")
@RequiredArgsConstructor
public class AdminMenuController {

    private final MenuItemService menuItemService;
    private final MenuCategoryService menuCategoryService;

    @GetMapping
    public String showMenuManagement(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String status,
            Model model) {

        List<MenuItem> menuItems;

        if (categoryId != null && status != null && !status.isEmpty()) {
            menuItems = menuItemService.getMenuItemsByCategoryIdAndStatus(
                    categoryId, MenuItemStatus.valueOf(status));
        } else if (categoryId != null) {
            menuItems = menuItemService.getMenuItemsByCategoryId(categoryId);
        } else if (status != null && !status.isEmpty()) {
            menuItems = menuItemService.getMenuItemsByStatus(MenuItemStatus.valueOf(status));
        } else {
            menuItems = menuItemService.getAllMenuItems();
        }

        model.addAttribute("menuItems", menuItems);
        model.addAttribute("categories", menuCategoryService.getAllCategories());
        model.addAttribute("isAdminPage", true);
        return "admin/menu-management";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        model.addAttribute("categories", menuCategoryService.getAllCategories());
        model.addAttribute("isAdminPage", true);
        return "admin/menu-add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        if (menuItem == null) {
            return "redirect:/admin/menu-management";
        }
        model.addAttribute("menuItem", menuItem);
        model.addAttribute("categories", menuCategoryService.getAllCategories());
        model.addAttribute("isAdminPage", true);
        return "admin/menu-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMenuItem(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer categoryId,
            @RequestParam String imageUrl,
            RedirectAttributes redirectAttributes) {

        try {
            MenuItem menuItem = menuItemService.getMenuItemById(id);

            if (menuItem == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy món ăn");
                return "redirect:/admin/menu-management";
            }

            menuItemService.updateMenuItem(id, name, description, price, categoryId, imageUrl);
            redirectAttributes.addFlashAttribute("success", "Cập nhật món ăn thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật món ăn: " + e.getMessage());
        }

        return "redirect:/admin/menu-management";
    }

    @PostMapping("/{id}/toggle-status")
    public String toggleMenuItemStatus(
            @PathVariable Integer id,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        try {
            MenuItem menuItem = menuItemService.getMenuItemById(id);

            if (menuItem == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy món ăn");
                return "redirect:/admin/menu-management";
            }

            MenuItemStatus newStatus = MenuItemStatus.valueOf(status);
            menuItemService.updateMenuItemStatus(id, newStatus);

            String statusMessage = newStatus == MenuItemStatus.AVAILABLE ? "bật" : "tắt";
            redirectAttributes.addFlashAttribute("success", "Đã " + statusMessage + " món ăn thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/menu-management";
    }

    @PostMapping("/toggle-multiple")
    public String toggleMultipleStatus(
            @RequestParam(value = "selectedItems", required = false) List<Integer> selectedItems,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        if (selectedItems == null || selectedItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ít nhất một món ăn");
            return "redirect:/admin/menu-management";
        }

        try {
            MenuItemStatus newStatus = MenuItemStatus.valueOf(status);
            int count = 0;

            for (Integer id : selectedItems) {
                menuItemService.updateMenuItemStatus(id, newStatus);
                count++;
            }

            String statusMessage = newStatus == MenuItemStatus.AVAILABLE ? "bật" : "tắt";
            redirectAttributes.addFlashAttribute("success", "Đã " + statusMessage + " " + count + " món ăn thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/menu-management";
    }
}