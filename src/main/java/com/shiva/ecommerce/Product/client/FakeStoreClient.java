package com.shiva.ecommerce.Product.client;

import com.shiva.ecommerce.Product.Dto.FakeStoreProduct;
import com.shiva.ecommerce.Product.Dto.ProductRequest;
import com.shiva.ecommerce.Exception.ProductNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FakeStoreClient {

    private final WebClient webClient;

    public FakeStoreClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<FakeStoreProduct> getProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(FakeStoreProduct.class)
                .collectList()
                .block();
    }

    public FakeStoreProduct  getProductById(Long id) {
        FakeStoreProduct product = webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(
                                new ProductNotFoundException(id))
                )
                .bodyToMono(FakeStoreProduct.class)
                .block();

        if (product == null || product.id() == null) {
            throw new ProductNotFoundException(id);
        }

        return product;
    }

    public FakeStoreProduct createProduct(ProductRequest request) {
        return webClient.post()
                .uri("/products")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FakeStoreProduct.class)
                .block();
    }
}
