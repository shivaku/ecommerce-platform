package com.shiva.ecommerce.Order.Dto;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long productId,
    Integer quantity,
    BigDecimal price
) {}
