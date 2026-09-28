package com.shiva.ecommerce.Order.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull
        Long productId,

        @Min(1)
        Integer quantity
) {}
