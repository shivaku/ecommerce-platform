package com.shiva.ecommerce.Product.provider;

import com.shiva.ecommerce.Product.Dto.FakeStoreProduct;
import com.shiva.ecommerce.Product.Dto.ProductRequest;
import com.shiva.ecommerce.Product.Dto.ProductResponse;
import com.shiva.ecommerce.Product.client.FakeStoreClient;
import com.shiva.ecommerce.Product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FakeStoreProductProvider implements ProductProvider {
    private final FakeStoreClient fakeStoreClient;

    public ProductResponse createProduct(ProductRequest request){
        return null;
    }
    public FakeStoreProductProvider(FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @Override
    public List<ProductResponse> getProducts() {
        return fakeStoreClient.getProducts().stream()
                .map(this::toProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return toProductResponse(
                fakeStoreClient.getProductById(id)
        );
    }

    private ProductResponse toProductResponse(FakeStoreProduct product) {
        return new ProductResponse(
                product.id(),
                product.title(),
                product.price(),
                product.description(),
                product.image(),
                product.stockQuantity()
        );
    }
}
