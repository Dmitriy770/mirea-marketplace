package com.mirea.app.models;

public record WashingMachineModel(
        Integer id,
        String producer,
        double tankVolume,
        String sellerUsername,
        String productType,
        double price,
        String name
) {

}
