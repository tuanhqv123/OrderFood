package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.model.OrderStatus;
import com.restaurant.orderfood.model.RestaurantOrder;
import com.restaurant.orderfood.model.RestaurantTable;
import com.restaurant.orderfood.service.MenuService;
import com.restaurant.orderfood.service.OrderService;
import com.restaurant.orderfood.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final TableService tableService;
    private final MenuService menuService;
    private final OrderService orderService;

    @GetMapping
    public String adminDashboard(Model model) {
        List<RestaurantTable> tables = tableService.getAllTables();
        List<RestaurantOrder> pendingOrders = orderService.getOrdersByStatus(OrderStatus.PENDING);
        List<RestaurantOrder> preparingOrders = orderService.getOrdersByStatus(OrderStatus.PREPARING);
        List<RestaurantOrder> readyOrders = orderService.getOrdersByStatus(OrderStatus.READY);

        model.addAttribute("tables", tables);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("preparingOrders", preparingOrders);
        model.addAttribute("readyOrders", readyOrders);

        return "admin/dashboard";
    }

    // Table Management
    @GetMapping("/tables")
    public String showTables(Model model) {
        List<RestaurantTable> tables = tableService.getAllTables();
        model.addAttribute("tables", tables);
        return "admin/tables";
    }

    @PostMapping("/tables/update-status")
    public String updateTableStatus(
            @RequestParam Integer tableId,
            @RequestParam RestaurantTable.TableStatus status,
            RedirectAttributes redirectAttributes) {

        try {
            tableService.updateTableStatus(tableId, status);
            redirectAttributes.addFlashAttribute("success", "Table status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/tables";
    }

    // Menu Management - Redirect to new menu management page
    @GetMapping("/menu")
    public String redirectToMenuManagement() {
        return "redirect:/admin/menu-management";
    }

    // Order Management
    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<RestaurantOrder> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/orders/filter")
    public String filterOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<RestaurantOrder> orders;

        if (status != null && date != null) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            orders = orderService.getOrdersByDateRange(start, end);
            orders = orders.stream()
                    .filter(order -> order.getStatus() == status)
                    .toList();
        } else if (status != null) {
            orders = orderService.getOrdersByStatus(status);
        } else if (date != null) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            orders = orderService.getOrdersByDateRange(start, end);
        } else {
            orders = orderService.getAllOrders();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedDate", date);

        return "admin/orders";
    }

    @GetMapping("/orders/{id}")
    public String showOrderDetails(@PathVariable Integer id, Model model) {
        RestaurantOrder order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "admin/order-details";
    }

    @PostMapping("/orders/update-status/{id}")
    public String updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam OrderStatus status,
            RedirectAttributes redirectAttributes) {

        try {
            orderService.updateOrderStatus(id, status);
            redirectAttributes.addFlashAttribute("success", "Order status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/orders/" + id;
    }
}