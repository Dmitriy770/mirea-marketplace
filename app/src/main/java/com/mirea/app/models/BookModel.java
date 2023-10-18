package com.mirea.app.models;

public record BookModel(
        Integer id,
        String author,
        String sellerUsername,
        String productType,
        double price,
        String title
) {
}
