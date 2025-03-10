package com.restaurant.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long tableId;
    private List<CartItem> items;
    private BigDecimal total;

    public Cart(Long tableId) {
        this.tableId = tableId;
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        calculateTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void calculateTotal() {
        this.total = items.stream()
                .map(item -> item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}