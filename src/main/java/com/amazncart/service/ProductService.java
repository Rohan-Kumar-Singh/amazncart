package com.amazncart.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazncart.exception.ProductNotFoundException;
import com.amazncart.model.Product;
import com.amazncart.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @Autowired
    private PromotionService promotionService;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Product createProduct(Product product){
        product.setArrivalDate(LocalDateTime.now());

        logger.info("Creating product with name: {}", product.getProductName());

        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product){

        Product existingProduct;

        try{
            existingProduct = productRepository.findById(productId).get();
            existingProduct.updateDetails(product);
            return productRepository.save(existingProduct);
        }catch(NoSuchElementException e){
            logger.error("Product not found with id: " + productId);
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }

    }

    public void deleteProduct(Long productId){

        Product existingProduct;

        try{
            existingProduct = productRepository.findById(productId).get();
            productRepository.delete(existingProduct);
        }catch(NoSuchElementException e){
            logger.error("Product not found with id: " + productId);
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }

    }

    public List<Product> getProducts(String promotion) {

        logger.info("Fetching all products");

        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            product.setPrice(currencyConversionService.convertToINR(product.getPrice(), product.getCurrency()));
            product.setDiscount(promotionService.applyPromotion(product, promotion));
        });
        return products;
    }    
}
