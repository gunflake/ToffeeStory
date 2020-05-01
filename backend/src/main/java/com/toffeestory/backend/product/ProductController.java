package com.toffeestory.backend.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductCategoryRepository productCategoryRepository;
    private final ToppingCategoryRepository toppingCategoryRepository;
    private final ProductRepository productRepository;
    private final ToppingRepository toppingRepository;

    @GetMapping(path = "/getProductCategories")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    @GetMapping(path = "/getProducts")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
