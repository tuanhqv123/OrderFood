package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.model.MenuItem;
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

    @GetMapping
    public String showMenuManagement(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            Model model) {

        List<MenuItem> menuItems;

        if (category != null && !category.isEmpty() && status != null && !status.isEmpty()) {
            menuItems = menuItemService.getMenuItemsByCategoryAndStatus(
                    category, MenuItem.MenuItemStatus.valueOf(status));
        } else if (category != null && !category.isEmpty()) {
            menuItems = menuItemService.getMenuItemsByCategory(category);
        } else if (status != null && !status.isEmpty()) {
            menuItems = menuItemService.getMenuItemsByStatus(MenuItem.MenuItemStatus.valueOf(status));
        } else {
            menuItems = menuItemService.getAllMenuItems();
        }

        model.addAttribute("menuItems", menuItems);
        model.addAttribute("isAdminPage", true);
        return "admin/menu-management";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        model.addAttribute("isAdminPage", true);
        return "admin/menu-add";
    }

    @PostMapping("/edit/{id}")
    public String updateMenuItem(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam String category,
            @RequestParam String imageUrl,
            RedirectAttributes redirectAttributes) {

        try {
            MenuItem menuItem = menuItemService.getMenuItemById(id);

            if (menuItem == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy món ăn");
                return "redirect:/admin/menu-management";
            }

            menuItem.setName(name);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            menuItem.setImageUrl(imageUrl);

            menuItemService.updateMenuItem(id, name, price, category, imageUrl);
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

            MenuItem.MenuItemStatus newStatus = MenuItem.MenuItemStatus.valueOf(status);
            menuItemService.updateMenuItemStatus(id, newStatus);

            String statusMessage = newStatus == MenuItem.MenuItemStatus.AVAILABLE ? "bật" : "tắt";
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
            MenuItem.MenuItemStatus newStatus = MenuItem.MenuItemStatus.valueOf(status);
            int count = 0;

            for (Integer id : selectedItems) {
                menuItemService.updateMenuItemStatus(id, newStatus);
                count++;
            }

            String statusMessage = newStatus == MenuItem.MenuItemStatus.AVAILABLE ? "bật" : "tắt";
            redirectAttributes.addFlashAttribute("success", "Đã " + statusMessage + " " + count + " món ăn thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/menu-management";
    }
}