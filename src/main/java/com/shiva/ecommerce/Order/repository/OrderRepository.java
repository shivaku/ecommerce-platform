package com.shiva.ecommerce.Order.repository;

import com.shiva.ecommerce.Order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
