package com.app.product_service.service;

import com.app.product_service.dto.ProductRequest;
import com.app.product_service.dto.ProductResponse;
import com.app.product_service.model.Product;
import com.app.product_service.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .skuCode(productRequest.skuCode())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepo.save(product);
        log.info("Product {} is saved", product.getId());
        return mapToProductResponse(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .skuCode(product.getSkuCode())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
