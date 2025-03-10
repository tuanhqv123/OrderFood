package com.restaurant.service.impl;

import com.restaurant.model.Cart;
import com.restaurant.model.CartItem;
import com.restaurant.model.MenuItem;
import com.restaurant.service.CartService;
import com.restaurant.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {

    private final Map<Long, Cart> carts = new ConcurrentHashMap<>();

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public Cart getCart(Long tableId) {
        return carts.computeIfAbsent(tableId, Cart::new);
    }

    @Override
    public Cart addToCart(Long tableId, Long menuItemId, Integer quantity) {
        Cart cart = getCart(tableId);
        MenuItem menuItem = menuItemService.getMenuItem(menuItemId);

        if (menuItem != null) {
            CartItem existingItem = cart.getItems().stream()
                    .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                cart.getItems().add(new CartItem(menuItem, quantity));
            }

            cart.calculateTotal();
        }

        return cart;
    }

    @Override
    public Cart updateItemQuantity(Long tableId, Long menuItemId, Integer quantity) {
        Cart cart = getCart(tableId);

        cart.getItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cart.getItems().remove(item);
                    } else {
                        item.setQuantity(Math.min(quantity, 10)); // Maximum quantity is 10
                    }
                });

        cart.calculateTotal();
        return cart;
    }

    @Override
    public Cart removeFromCart(Long tableId, Long menuItemId) {
        Cart cart = getCart(tableId);
        cart.getItems().removeIf(item -> item.getMenuItem().getId().equals(menuItemId));
        cart.calculateTotal();
        return cart;
    }
}