package com.amazncart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    private double amount;
    private String discountTag;

    // public Discount(double amount, String discountTag) {
    //     this.amount = amount;
    //     this.discountTag = discountTag;
    // }

    // public double getAmount() {
    //     return amount;
    // }

    // public void setAmount(double amount) {
    //     this.amount = amount;
    // }

    // public String getDiscountTag() {
    //     return discountTag;
    // }

    // public void setDiscountTag(String discountTag) {
    //     this.discountTag = discountTag;
    // }
    
}
