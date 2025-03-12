package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{tableId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Integer tableId) {
        CartDto cart = cartService.getCart(tableId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{tableId}/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @PathVariable Integer tableId,
            @RequestParam Integer menuItemId,
            @RequestParam Integer quantity) {

        Map<String, Object> response = new HashMap<>();

        try {
            CartDto cart = cartService.addToCart(tableId, menuItemId, quantity);
            response.put("success", true);
            response.put("message", "Đã thêm món vào giỏ hàng");
            response.put("cart", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/{tableId}/update")
    public ResponseEntity<Map<String, Object>> updateCartItem(
            @PathVariable Integer tableId,
            @RequestParam Integer menuItemId,
            @RequestParam Integer quantity) {

        Map<String, Object> response = new HashMap<>();

        try {
            CartDto cart = cartService.updateItemQuantity(tableId, menuItemId, quantity);
            response.put("success", true);
            response.put("message", "Đã cập nhật số lượng");
            response.put("cart", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/{tableId}/remove")
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @PathVariable Integer tableId,
            @RequestParam Integer menuItemId) {

        Map<String, Object> response = new HashMap<>();

        try {
            CartDto cart = cartService.removeFromCart(tableId, menuItemId);
            response.put("success", true);
            response.put("message", "Đã xóa món khỏi giỏ hàng");
            response.put("cart", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/{tableId}/clear")
    public ResponseEntity<Map<String, Object>> clearCart(
            @PathVariable Integer tableId) {

        Map<String, Object> response = new HashMap<>();

        try {
            cartService.clearCart(tableId);
            CartDto cart = cartService.getCart(tableId); // Get the empty cart after clearing
            response.put("success", true);
            response.put("message", "Đã xóa giỏ hàng");
            response.put("cart", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}