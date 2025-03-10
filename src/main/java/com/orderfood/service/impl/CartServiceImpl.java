package com.orderfood.service.impl;

import com.orderfood.model.Cart;
import com.orderfood.model.CartItem;
import com.orderfood.model.MenuItem;
import com.orderfood.service.CartService;
import com.orderfood.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {

    private final Map<Long, Cart> cartMap = new ConcurrentHashMap<>();

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public Cart getCartByTableId(Long tableId) {
        return cartMap.computeIfAbsent(tableId, Cart::new);
    }

    @Override
    public Cart addToCart(Long tableId, Long menuItemId, Integer quantity) {
        Cart cart = getCartByTableId(tableId);
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);

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
    public Cart updateQuantity(Long tableId, Long menuItemId, Integer quantity) {
        Cart cart = getCartByTableId(tableId);

        cart.getItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity > 0) {
                        item.setQuantity(quantity);
                    } else {
                        cart.getItems().remove(item);
                    }
                });

        cart.calculateTotal();
        return cart;
    }

    @Override
    public Cart removeFromCart(Long tableId, Long menuItemId) {
        Cart cart = getCartByTableId(tableId);

        cart.getItems().removeIf(item -> item.getMenuItem().getId().equals(menuItemId));
        cart.calculateTotal();

        return cart;
    }
}