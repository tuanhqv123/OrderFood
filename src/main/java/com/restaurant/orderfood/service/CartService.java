package com.restaurant.orderfood.service;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.model.MenuItem;

public interface CartService {
    CartDto getCart(Integer tableId);

    CartDto addToCart(Integer tableId, Integer menuItemId, Integer quantity);

    CartDto updateCartItem(Integer tableId, Integer menuItemId, Integer quantity);

    CartDto removeFromCart(Integer tableId, Integer menuItemId);

    void clearCart(Integer tableId);
}