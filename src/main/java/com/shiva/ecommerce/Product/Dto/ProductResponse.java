package com.shiva.ecommerce.Product.Dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String title,
        BigDecimal price,
        String description,
        String image,
        Integer stockQuantity
) {}

