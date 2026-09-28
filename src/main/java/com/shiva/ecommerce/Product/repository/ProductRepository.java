package com.shiva.ecommerce.Product.repository;

import com.shiva.ecommerce.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface  ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

    @Modifying
    @Query("""
    UPDATE Product p
    SET p.stockQuantity = p.stockQuantity - :quantity
    WHERE p.id = :productId
      AND p.stockQuantity >= :quantity
""")
    int deductStock(
            @Param("productId") Long productId,
            @Param("quantity") Integer quantity
    );
}
