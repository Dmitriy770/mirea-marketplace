package com.mirea.app.services;

import com.mirea.app.models.CartModel;
import com.mirea.app.models.StorageModel;
import com.mirea.app.storage.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    private final StorageRepository storageRepository;
    private final CartRepository cartRepository;


    public CartService(StorageRepository storageRepository, CartRepository cartRepository) {
        this.storageRepository = storageRepository;
        this.cartRepository = cartRepository;
    }

    public void addProduct(CartModel model) {
        var productOnStorage = storageRepository.get(model.productType(), model.productId());
        if (productOnStorage.amount() >= model.amount()) {
            cartRepository.add(model);
        }
    }

    public void buy(String username) {
        var products = cartRepository.getClientCart(username);

        var productsOnStorage = new ArrayList<StorageModel>();
        for (var product : products) {
            var productOnStorage = storageRepository.get(product.productType(), product.productId());
            productsOnStorage.add(productOnStorage);
            if (productOnStorage.amount() < product.amount()) {
                return;
            }
        }

        for (int i = 0; i < products.size(); i++) {
            var newStorageModel = new StorageModel(
                    products.get(i).productType(),
                    products.get(i).productId(),
                    productsOnStorage.get(i).amount() - products.get(i).amount()
            );
            storageRepository.updateAmount(newStorageModel);
        }

        for (var product : products) {
            cartRepository.delete(product.username(), product.productType(), product.productId());
        }
    }
}
