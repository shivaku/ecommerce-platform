package com.shiva.ecommerce.Exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, Integer quantity) {
        super("Insufficient stock for product " + productId +
                ". Requested: " + quantity);
    }
}
