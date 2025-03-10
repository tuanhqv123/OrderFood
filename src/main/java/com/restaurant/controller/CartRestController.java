package com.restaurant.controller;

import com.restaurant.model.Cart;
import com.restaurant.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @PostMapping("/update-quantity")
    public ResponseEntity<?> updateQuantity(
            @RequestParam Long menuItemId,
            @RequestParam Integer quantity,
            @RequestParam Long tableId) {

        Cart cart = cartService.updateItemQuantity(tableId, menuItemId, quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("cartTotal", cart.getTotal());
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addToCart(
            @RequestParam Long menuItemId,
            @RequestParam Integer quantity,
            @RequestParam Long tableId) {

        Cart cart = cartService.addToCart(tableId, menuItemId, quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("cartTotal", cart.getTotal());
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-item")
    public ResponseEntity<?> removeFromCart(
            @RequestParam Long menuItemId,
            @RequestParam Long tableId) {

        Cart cart = cartService.removeFromCart(tableId, menuItemId);

        Map<String, Object> response = new HashMap<>();
        response.put("cartTotal", cart.getTotal());
        response.put("success", true);

        return ResponseEntity.ok(response);
    }
}