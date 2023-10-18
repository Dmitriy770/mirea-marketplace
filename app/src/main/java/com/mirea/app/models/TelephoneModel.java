package com.mirea.app.models;

public record TelephoneModel(
        Integer id,
        double batteryCapacity,
        double price,
        String sellerUsername,
        String productType,
        String name,
        String producer
) {
}
