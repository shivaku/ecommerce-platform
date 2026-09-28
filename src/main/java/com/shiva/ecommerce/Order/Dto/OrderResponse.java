package com.shiva.ecommerce.Order.Dto;

import com.shiva.ecommerce.Order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        Long customerId,
        OrderStatus orderStatus,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {}
