package com.restaurant.orderfood.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private Long tableId;
    private List<CartItem> items = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    public Cart(Long tableId) {
        this.tableId = tableId;
    }

    public void calculateTotal() {
        total = items.stream()
                .map(item -> item.getMenuItem().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}