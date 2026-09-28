package com.shiva.ecommerce.Product.provider;

import com.shiva.ecommerce.Product.Dto.ProductRequest;
import com.shiva.ecommerce.Product.Dto.ProductResponse;

import java.util.List;

public interface ProductProvider {
    List<ProductResponse> getProducts();

    ProductResponse getProductById(Long id);

    ProductResponse createProduct(ProductRequest request);
}
