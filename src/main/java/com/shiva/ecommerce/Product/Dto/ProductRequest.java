package com.shiva.ecommerce.Product.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest (
    @NotBlank String title,
    @NotNull @Positive BigDecimal price,
    @NotBlank String description,
    @NotBlank String image,
    @NotNull @Positive Integer stockQuantity
){}
