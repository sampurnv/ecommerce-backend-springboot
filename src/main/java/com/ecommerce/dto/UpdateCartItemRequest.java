package com.ecommerce.dto;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long userId;
    private Long itemId;
    private Integer quantity;
}