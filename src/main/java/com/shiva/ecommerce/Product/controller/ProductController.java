package com.shiva.ecommerce.Product.controller;

import com.shiva.ecommerce.Product.Dto.ProductRequest;
import com.shiva.ecommerce.Product.Dto.ProductResponse;
import com.shiva.ecommerce.Product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

//    @GetMapping("/{productId}")
//    public ProductResponse getProductsById(@PathVariable Long productId) {
//        return productService.getProductById(productId);
//    }

    @PutMapping("/{product_id}")
    public  ProductResponse updateProduct(
                @PathVariable("product_id") Long productId,
                @Valid @RequestBody ProductRequest productRequest
    ){

        return productService.updateProductById(productId, productRequest);

    }
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

    @DeleteMapping("/{product_id}")
    public void deleteProductById(@PathVariable("product_id")  Long productId) {
        productService.deleteProductById(productId);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
}
