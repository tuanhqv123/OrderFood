package com.restaurant.orderfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer id;
    private Integer tableId;
    private String status;
    private LocalDateTime createdAt;
    private Double total;
    private List<OrderItemDto> items;
}