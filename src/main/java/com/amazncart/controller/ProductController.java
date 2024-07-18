package com.amazncart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazncart.model.Product;
import com.amazncart.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

     @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long product_id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product_id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long product_id) {
        productService.deleteProduct(product_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam String promotion) {
        List<Product> products = productService.getProducts(promotion);
        return ResponseEntity.ok(products);
    }
    
}
