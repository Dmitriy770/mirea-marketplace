package com.mirea.app.models;

public record CartModel(
        String username,
        String productType,
        int productId,
        int amount
) {
}
