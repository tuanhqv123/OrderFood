package com.restaurant.orderfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Integer id;
    private Integer menuItemId;
    private String name;
    private Double price;
    private Integer quantity;
    private Double subtotal;
}