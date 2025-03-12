package com.restaurant.orderfood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Integer tableId;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    public void addItem(CartItemDto item) {
        // Check if item already exists in cart
        for (CartItemDto existingItem : items) {
            if (existingItem.getMenuItemId().equals(item.getMenuItemId())) {
                // Update quantity and subtotal
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                existingItem.calculateSubtotal();
                updateTotal();
                return;
            }
        }

        // Add new item
        item.calculateSubtotal();
        items.add(item);
        updateTotal();
    }

    public void updateItem(Integer menuItemId, Integer quantity) {
        for (CartItemDto item : items) {
            if (item.getMenuItemId().equals(menuItemId)) {
                item.setQuantity(quantity);
                item.calculateSubtotal();
                updateTotal();
                return;
            }
        }
    }

    public void removeItem(Integer menuItemId) {
        items.removeIf(item -> item.getMenuItemId().equals(menuItemId));
        updateTotal();
    }

    public void clear() {
        items.clear();
        total = BigDecimal.ZERO;
    }

    public void updateTotal() {
        total = items.stream()
                .map(CartItemDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateTotal() {
        total = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}