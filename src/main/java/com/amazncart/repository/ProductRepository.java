package com.amazncart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazncart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
