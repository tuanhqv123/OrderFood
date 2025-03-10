package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.dto.OrderDto;
import com.restaurant.orderfood.model.RestaurantOrder;
import com.restaurant.orderfood.service.CartService;
import com.restaurant.orderfood.service.OrderService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/checkout")
    public String showCheckout(@RequestParam Integer tableId, Model model) {
        CartDto cart = cartService.getCart(tableId);

        if (cart.getItems().isEmpty()) {
            model.addAttribute("error", "Your cart is empty");
            return "error";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("tableId", tableId);

        return "checkout";
    }

    @PostMapping("/place")
    public String placeOrder(
            @RequestParam Integer tableId,
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits") String phoneNumber,
            RedirectAttributes redirectAttributes) {

        try {
            CartDto cart = cartService.getCart(tableId);

            if (cart.getItems().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Your cart is empty");
                return "redirect:/menu?table=" + tableId;
            }

            RestaurantOrder order = orderService.createOrder(cart, phoneNumber);

            // Clear cart after successful order
            cartService.clearCart(tableId);

            redirectAttributes.addFlashAttribute("success", "Order placed successfully");
            return "redirect:/order/status?orderId=" + order.getId() + "&tableId=" + tableId;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/order/checkout?tableId=" + tableId;
        }
    }

    @GetMapping("/status")
    public String showOrderStatus(@RequestParam Integer orderId, @RequestParam Integer tableId, Model model) {
        try {
            RestaurantOrder order = orderService.getOrderById(orderId);
            model.addAttribute("order", order);
            model.addAttribute("tableId", tableId);
            return "order-status";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/refresh/{id}")
    public String refreshOrderStatus(@PathVariable Integer id, @RequestParam Integer tableId,
            RedirectAttributes redirectAttributes) {
        try {
            // Simply redirect to the status page to refresh the data
            return "redirect:/order/status?orderId=" + id + "&tableId=" + tableId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/menu?table=" + tableId;
        }
    }

    @PostMapping("/cancel")
    public String cancelOrder(
            @RequestParam Integer orderId,
            @RequestParam Integer tableId,
            RedirectAttributes redirectAttributes) {

        try {
            // Add a 2-second delay for ethical friction
            Thread.sleep(2000);

            orderService.cancelOrder(orderId);
            redirectAttributes.addFlashAttribute("success", "Order cancelled successfully");
            return "redirect:/menu?table=" + tableId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/order/status?orderId=" + orderId + "&tableId=" + tableId;
        }
    }

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/orders")
    public static class OrderApiController {

        private final OrderService orderService;

        @PostMapping("/create")
        public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> request) {
            Integer tableId = (Integer) request.get("tableId");

            if (tableId == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Table ID is required");
                return ResponseEntity.badRequest().body(response);
            }

            try {
                OrderDto order = orderService.createOrderFromCart(tableId);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("orderId", order.getId());
                response.put("message", "Order created successfully");

                return ResponseEntity.ok(response);
            } catch (Exception e) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        }
    }
}