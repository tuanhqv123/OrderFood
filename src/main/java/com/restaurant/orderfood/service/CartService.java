package com.restaurant.orderfood.service;

import com.restaurant.orderfood.dto.CartDto;

public interface CartService {
    CartDto getCart(Integer tableId);

    CartDto addToCart(Integer tableId, Integer menuItemId, Integer quantity);

    CartDto updateItemQuantity(Integer tableId, Integer menuItemId, Integer quantity);

    CartDto removeFromCart(Integer tableId, Integer menuItemId);

    void clearCart(Integer tableId);
}