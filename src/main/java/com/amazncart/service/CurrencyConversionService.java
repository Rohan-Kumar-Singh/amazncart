package com.amazncart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConversionService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);

    @Value("${exchange.api.url}")
    private String exchangeApiUrl;

    @Value("${exchange.api.key}")
    private String exchangeApiKey;

    public double convertToINR(double amount, String currency) {
        if ("INR".equalsIgnoreCase(currency)) {
            return amount;
        }
        String url = String.format("%s/latest?api_key=%s&symbols=INR&base=%s", exchangeApiUrl, exchangeApiKey, currency);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ExchangeRatesResponse response = restTemplate.getForObject(url, ExchangeRatesResponse.class);
            if (response != null && response.getRates() != null && response.getRates().containsKey("INR")) {
                double conversionRate = response.getRates().get("INR");
                logger.info("Converted amount: {} {} to INR: {}", amount, currency, amount * conversionRate);
                return amount * conversionRate;
            } else {
                logger.error("Failed to retrieve conversion rate from response. Response: {}", response);
                throw new RuntimeException("Failed to retrieve conversion rate");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error occurred while fetching exchange rates: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch exchange rates", e);
        } catch (Exception e) {
            logger.error("Error occurred while converting currency: {}", e.getMessage());
            throw new RuntimeException("Currency conversion error", e);
        }
    }
}
