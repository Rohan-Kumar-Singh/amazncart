package com.amazncart.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.amazncart.dto.Discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sku;

    private String category;
    private Integer inventory;
    private Float rating;
    private String currency;
    private Double price;
    private String origin;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Transient
    private Discount discount;

    public void updateDetails(Product product){
        this.sku = product.getSku();
        this.category = product.getCategory();
        this.inventory = product.getInventory();
        this.rating = product.getRating();
        this.currency = product.getCurrency();
        this.price = product.getPrice();
        this.origin = product.getOrigin();
        this.productName = product.getProductName();
        
    }

    public Product(double price) {
        this.price = price;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        throw new UnsupportedOperationException("Unimplemented method 'setDiscount'");
    }
    
}
