package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.RestaurantTable;
import com.restaurant.orderfood.service.CartService;
import com.restaurant.orderfood.service.MenuItemService;
import com.restaurant.orderfood.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuItemService menuItemService;
    private final CartService cartService;
    private final TableService tableService;

    @GetMapping
    public String showMenu(@RequestParam(name = "table") Integer table, Model model) {
        // Lấy thông tin bàn
        RestaurantTable restaurantTable = tableService.getOrCreateTable(table);

        // Lấy danh sách món ăn có sẵn
        List<MenuItem> menuItems = menuItemService.getMenuItemsByStatus(MenuItem.MenuItemStatus.AVAILABLE);

        // Lấy giỏ hàng hiện tại
        CartDto cart = cartService.getCart(table);

        model.addAttribute("menuItems", menuItems);
        model.addAttribute("tableId", table);
        model.addAttribute("cart", cart);

        return "menu";
    }

    @GetMapping("/category/{category}")
    public String showMenuByCategory(
            @PathVariable String category,
            @RequestParam(name = "table") Integer table,
            Model model) {

        // Lấy thông tin bàn
        RestaurantTable restaurantTable = tableService.getOrCreateTable(table);

        // Lấy danh sách món ăn theo danh mục và có sẵn
        List<MenuItem> menuItems = menuItemService.getMenuItemsByCategoryAndStatus(
                category, MenuItem.MenuItemStatus.AVAILABLE);

        // Lấy giỏ hàng hiện tại
        CartDto cart = cartService.getCart(table);

        model.addAttribute("menuItems", menuItems);
        model.addAttribute("category", category);
        model.addAttribute("tableId", table);
        model.addAttribute("cart", cart);

        return "menu";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam Integer tableId,
            @RequestParam Integer menuItemId,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {

        try {
            cartService.addToCart(tableId, menuItemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Đã thêm món vào giỏ hàng");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/menu?table=" + tableId;
    }

    @PostMapping("/cart/update")
    public String updateCartItem(
            @RequestParam Integer tableId,
            @RequestParam Integer menuItemId,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {

        try {
            cartService.updateCartItem(tableId, menuItemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật giỏ hàng");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/order/checkout?tableId=" + tableId;
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(
            @RequestParam Integer tableId,
            @RequestParam Integer menuItemId,
            RedirectAttributes redirectAttributes) {

        try {
            cartService.removeFromCart(tableId, menuItemId);
            redirectAttributes.addFlashAttribute("success", "Đã xóa món khỏi giỏ hàng");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/order/checkout?tableId=" + tableId;
    }

    @PostMapping("/cart/clear")
    public String clearCart(
            @RequestParam Integer tableId,
            RedirectAttributes redirectAttributes) {

        try {
            cartService.clearCart(tableId);
            redirectAttributes.addFlashAttribute("success", "Đã xóa giỏ hàng");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/menu?table=" + tableId;
    }
}