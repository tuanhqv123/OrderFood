package com.orderfood.service;

import com.orderfood.model.Cart;

public interface CartService {
    Cart getCartByTableId(Long tableId);

    Cart addToCart(Long tableId, Long menuItemId, Integer quantity);

    Cart updateQuantity(Long tableId, Long menuItemId, Integer quantity);

    Cart removeFromCart(Long tableId, Long menuItemId);
}