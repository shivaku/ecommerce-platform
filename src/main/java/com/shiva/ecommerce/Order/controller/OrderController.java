package com.shiva.ecommerce.Order.controller;

import com.shiva.ecommerce.Order.Dto.OrderRequest;
import com.shiva.ecommerce.Order.Dto.OrderResponse;
import com.shiva.ecommerce.Order.entity.Order;
import com.shiva.ecommerce.Order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request
    ) {
        return orderService.createOrder(request);
    }
}
