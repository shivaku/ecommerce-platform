package com.shiva.ecommerce.Product.service;

import com.shiva.ecommerce.Product.Dto.ProductRequest;
import com.shiva.ecommerce.Product.Dto.ProductResponse;
import com.shiva.ecommerce.Product.entity.Product;
import com.shiva.ecommerce.Product.provider.ProductProvider;
import com.shiva.ecommerce.Exception.InsufficientStockException;
import com.shiva.ecommerce.Exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import com.shiva.ecommerce.Product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductProvider productProvider;
    private final ProductRepository productRepository;


    public ProductService(ProductProvider productProvider, ProductRepository productRepository) {
        this.productProvider = productProvider;
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts() {
        return productProvider.getProducts();
    }

    public ProductResponse getProductById(Long id) {
        Product product =  productRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new ProductNotFoundException(id)
        );

        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImage(),
                product.getStockQuantity()
        );
    }
    public ProductResponse updateProductById(Long productId, ProductRequest productRequest) {
        Product product =  productRepository.findById(productId).orElseThrow(()
                    -> new ProductNotFoundException(productId));

        product.setTitle(productRequest.title());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());
        product.setImage(productRequest.image());

        Product updateProduct = productRepository.save(product);
        return new ProductResponse(
                updateProduct.getId(),
                updateProduct.getTitle(),
                updateProduct.getPrice(),
                updateProduct.getDescription(),
                updateProduct.getImage(),
                updateProduct.getStockQuantity()
        );

    }
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setTitle(request.title());
        product.setPrice(request.price());
        product.setDescription(request.description());
        product.setImage(request.image());
        product.setStockQuantity(request.stockQuantity());
        Product saved = productRepository.save(product);

        return new ProductResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getPrice(),
                saved.getDescription(),
                saved.getImage(),
                saved.getStockQuantity()
        );
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository.findByActiveTrue()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getImage(),
                        product.getStockQuantity()
                ))
                .toList();
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setActive(false);
        productRepository.save(product);
    }

    @Transactional
    public void deductStock(Long productId, Integer quantity) {

        int updatedRows =
                productRepository.deductStock(productId, quantity);

        if (updatedRows == 0) {
            throw new InsufficientStockException(productId, quantity);
        }
    }
}
