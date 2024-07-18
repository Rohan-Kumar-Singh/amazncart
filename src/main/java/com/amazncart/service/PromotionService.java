package com.amazncart.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazncart.dto.Discount;
import com.amazncart.model.Product;

@Service
public class PromotionService {

    public Discount applyPromotion(Product product, String promotion) {
        switch (promotion) {
            case "SetA":
                return applyPromotionSetA(product);
            case "SetB":
                return applyPromotionSetB(product);
            default:
                return applyDefaultDiscount(product);
        }
    }

     private Discount applyPromotionSetA(Product product) {
        List<Discount> discounts = new ArrayList<>();
        if ("UAE".equalsIgnoreCase(product.getOrigin())) {
            discounts.add(new Discount(product.getPrice() * 0.06, "6% off"));
        }
        if (product.getRating() == 2) {
            discounts.add(new Discount(product.getPrice() * 0.04, "4% off"));
        } else if (product.getRating() < 2) {
            discounts.add(new Discount(product.getPrice() * 0.08, "8% off"));
        }
        if (Arrays.asList("electronics", "furnishing").contains(product.getCategory()) && product.getPrice() >= 500) {
            discounts.add(new Discount(100, "Rs 100 off"));
        }
        return getBestDiscount(discounts, product.getPrice());
    }

    private Discount applyPromotionSetB(Product product) {
        List<Discount> discounts = new ArrayList<>();
        if (product.getInventory() > 20) {
            discounts.add(new Discount(product.getPrice() * 0.12, "12% off"));
        }
        if (isNewArrival(product.getArrivalDate())) {
            discounts.add(new Discount(product.getPrice() * 0.07, "7% off"));
        }
        return getBestDiscount(discounts, product.getPrice());
    }

    private boolean isNewArrival(LocalDateTime arrivalDate) {
        return ChronoUnit.DAYS.between(arrivalDate, LocalDateTime.now()) <= 30;
    }

    private Discount applyDefaultDiscount(Product product) {
        if (product.getPrice() > 1000) {
            return new Discount(product.getPrice() * 0.02, "2% off");
        }
        return new Discount(0, "No discount");
    }

    private Discount getBestDiscount(List<Discount> discounts, double price) {
        Discount bestDiscount = discounts.stream()
                .max(Comparator.comparingDouble(Discount::getAmount))
                .orElse(new Discount(0, "No discount"));
        return bestDiscount.getAmount() == 0 && price > 1000 ? applyDefaultDiscount(new Product(price)) : bestDiscount;
    }
    
}
