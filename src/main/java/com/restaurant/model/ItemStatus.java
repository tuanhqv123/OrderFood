package com.restaurant.model;

import java.math.BigDecimal;

public class MenuItem {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private ItemStatus status;

    public MenuItem() {
    }

    public MenuItem(Long id, String name, String category, BigDecimal price, String imageUrl, ItemStatus status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}

public enum ItemStatus {
    AVAILABLE,
    UNAVAILABLE
}