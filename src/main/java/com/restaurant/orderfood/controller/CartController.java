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

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam Integer tableId,
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

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateCartItem(
            @RequestParam Integer tableId,
            @RequestParam Integer menuItemId,
            @RequestParam Integer quantity) {

        Map<String, Object> response = new HashMap<>();

        try {
            CartDto cart = cartService.updateCartItem(tableId, menuItemId, quantity);
            response.put("success", true);
            response.put("message", "Đã cập nhật giỏ hàng");
            response.put("cart", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @RequestParam Integer tableId,
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

    @PostMapping("/clear")
    public ResponseEntity<Map<String, Object>> clearCart(
            @RequestParam Integer tableId) {

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

    @GetMapping("/{tableId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Integer tableId) {
        CartDto cart = cartService.getCart(tableId);
        return ResponseEntity.ok(cart);
    }
}