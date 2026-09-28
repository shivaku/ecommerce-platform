package com.shiva.ecommerce.Order.service;

import com.shiva.ecommerce.Exception.InsufficientStockException;
import com.shiva.ecommerce.Exception.ProductNotFoundException;
import com.shiva.ecommerce.Order.Dto.OrderRequest;
import com.shiva.ecommerce.Order.Dto.OrderResponse;
import com.shiva.ecommerce.Order.entity.Order;
import com.shiva.ecommerce.Order.entity.OrderItem;
import com.shiva.ecommerce.Order.entity.OrderStatus;
import com.shiva.ecommerce.Order.repository.OrderRepository;
import com.shiva.ecommerce.Product.entity.Product;

import com.shiva.ecommerce.Product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiva.ecommerce.Order.Dto.OrderItemResponse;
@Service
public class OrderService {

        private final OrderRepository orderRepository;
        private final ProductRepository productRepository;

        public OrderService(OrderRepository orderRepository,
                                ProductRepository productRepository) {
            this.orderRepository = orderRepository;
            this.productRepository = productRepository;
        }

        @Transactional
        public OrderResponse createOrder(OrderRequest request) {
            Order order  = new Order();

            order.setCustomerId(request.customerId());
            order.setStatus(OrderStatus.CREATED);

            for(var itemRequest :  request.items()) {

                Product product = productRepository.findByIdAndActiveTrue(itemRequest.productId())
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                    itemRequest.productId()
                                ));
                int updated = productRepository.deductStock(
                        product.getId(),
                        itemRequest.quantity()
                );

                if (updated == 0) {
                    throw new InsufficientStockException(
                            product.getId(),
                            itemRequest.quantity()
                    );
                }
                OrderItem item = new OrderItem();

                item.setProductId(itemRequest.productId());
                item.setQuantity(itemRequest.quantity());
                item.setPrice(product.getPrice());

                order.addItem(item);
            }
            Order savedOrder = orderRepository.save(order);
            return new OrderResponse(
                    savedOrder.getOrderId(),
                    savedOrder.getCustomerId(),
                    savedOrder.getStatus(),
                    savedOrder.getCreatedAt(),
                    savedOrder.getItems()
                            .stream()
                            .map(item -> new OrderItemResponse(
                                    item.getProductId(),
                                    item.getQuantity(),
                                    item.getPrice()
                            ))
                            .toList()

            );
        }
}