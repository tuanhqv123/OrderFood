package com.restaurant.service;

import com.restaurant.model.Cart;

public interface CartService {
    Cart getCart(Long tableId);

    Cart addToCart(Long tableId, Long menuItemId, Integer quantity);

    Cart updateItemQuantity(Long tableId, Long menuItemId, Integer quantity);

    Cart removeFromCart(Long tableId, Long menuItemId);
}