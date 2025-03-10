package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.dto.CartDto;
import com.restaurant.orderfood.dto.CartItemDto;
import com.restaurant.orderfood.model.MenuItem;
import com.restaurant.orderfood.service.CartService;
import com.restaurant.orderfood.service.MenuService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final HttpSession session;
    private final MenuService menuService;

    private static final String CART_SESSION_KEY = "cart";

    @Override
    public CartDto getCart(Integer tableId) {
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION_KEY);

        if (cart == null) {
            cart = new CartDto();
            cart.setTableId(tableId);
            session.setAttribute(CART_SESSION_KEY, cart);
        }

        return cart;
    }

    @Override
    public CartDto addToCart(Integer tableId, Integer menuItemId, Integer quantity) {
        CartDto cart = getCart(tableId);

        // Get menu item
        MenuItem menuItem = menuService.getMenuItemById(menuItemId);

        // Check if item is available
        if (menuItem.getStatus() == MenuItem.MenuItemStatus.UNAVAILABLE) {
            throw new IllegalStateException("Menu item is unavailable");
        }

        // Create cart item
        CartItemDto cartItem = new CartItemDto();
        cartItem.setMenuItemId(menuItem.getId());
        cartItem.setName(menuItem.getName());
        cartItem.setPrice(menuItem.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setSubtotal(menuItem.getPrice().multiply(new BigDecimal(quantity)));
        cartItem.setMenuItem(menuItem);

        // Add to cart
        cart.addItem(cartItem);

        // Update session
        session.setAttribute(CART_SESSION_KEY, cart);

        return cart;
    }

    @Override
    public CartDto updateCartItem(Integer tableId, Integer menuItemId, Integer quantity) {
        CartDto cart = getCart(tableId);

        if (quantity <= 0) {
            return removeFromCart(tableId, menuItemId);
        }

        cart.updateItem(menuItemId, quantity);

        // Update session
        session.setAttribute(CART_SESSION_KEY, cart);

        return cart;
    }

    @Override
    public CartDto removeFromCart(Integer tableId, Integer menuItemId) {
        CartDto cart = getCart(tableId);

        cart.removeItem(menuItemId);

        // Update session
        session.setAttribute(CART_SESSION_KEY, cart);

        return cart;
    }

    @Override
    public void clearCart(Integer tableId) {
        CartDto cart = getCart(tableId);

        cart.clear();

        // Update session
        session.setAttribute(CART_SESSION_KEY, cart);
    }
}