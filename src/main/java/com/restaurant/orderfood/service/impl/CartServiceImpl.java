package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.dto.CartItemDto;
import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.service.CartService;
import com.restaurant.orderfood.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {

    private final Map<Integer, CartDto> carts = new ConcurrentHashMap<>();

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public CartDto getCart(Integer tableId) {
        return carts.computeIfAbsent(tableId, id -> {
            CartDto cart = new CartDto();
            cart.setTableId(id);
            cart.setItems(new ArrayList<>());
            cart.setTotal(BigDecimal.ZERO);
            return cart;
        });
    }

    @Override
    public CartDto addToCart(Integer tableId, Integer menuItemId, Integer quantity) {
        CartDto cart = getCart(tableId);
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);

        if (menuItem != null) {
            CartItemDto existingItem = cart.getItems().stream()
                    .filter(item -> item.getMenuItemId().equals(menuItemId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                CartItemDto newItem = new CartItemDto();
                newItem.setMenuItemId(menuItem.getId());
                newItem.setName(menuItem.getName());
                newItem.setPrice(menuItem.getPrice());
                newItem.setQuantity(quantity);
                cart.getItems().add(newItem);
            }

            calculateTotal(cart);
        }

        return cart;
    }

    @Override
    public CartDto updateItemQuantity(Integer tableId, Integer menuItemId, Integer quantity) {
        CartDto cart = getCart(tableId);

        cart.getItems().stream()
                .filter(item -> item.getMenuItemId().equals(menuItemId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cart.getItems().remove(item);
                    } else {
                        item.setQuantity(Math.min(quantity, 10)); // Maximum quantity is 10
                    }
                });

        calculateTotal(cart);
        return cart;
    }

    @Override
    public CartDto removeFromCart(Integer tableId, Integer menuItemId) {
        CartDto cart = getCart(tableId);
        cart.getItems().removeIf(item -> item.getMenuItemId().equals(menuItemId));
        calculateTotal(cart);
        return cart;
    }

    @Override
    public void clearCart(Integer tableId) {
        CartDto cart = getCart(tableId);
        cart.getItems().clear();
        cart.setTotal(BigDecimal.ZERO);
    }

    private void calculateTotal(CartDto cart) {
        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal(total);
    }
}