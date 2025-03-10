package com.orderfood.model;

public class OrderItem {
    private Long id;
    private MenuItem menuItem;
    private Integer quantity;
    private String note;

    public OrderItem(Long id, MenuItem menuItem, Integer quantity, String note) {
        this.id = id;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}