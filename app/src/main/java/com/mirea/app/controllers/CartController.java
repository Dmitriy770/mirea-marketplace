package com.mirea.app.controllers;

import com.mirea.app.models.CartModel;
import com.mirea.app.services.CartService;
import com.mirea.app.services.TokenService;
import com.mirea.app.storage.repositories.CartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final TokenService tokenService;

    public CartController(CartRepository cartRepository, CartService cartService, TokenService tokenService) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.tokenService = tokenService;
    }


    @PostMapping("/cart/add")
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody CartModel model) {
        if (tokenService.canGet(token)) {
            var user = tokenService.getUser(token);
            cartService.addProduct(new CartModel(
                    user.username(),
                    model.productType(),
                    model.productId(),
                    model.amount()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartModel>> get(@RequestHeader("Authorization") String token) {
        if (tokenService.canGet(token)) {
            var user = tokenService.getUser(token);
            return ResponseEntity.ok(cartRepository.getClientCart(user.username()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/cart/buy")
    public ResponseEntity<String> buy(@RequestHeader("Authorization") String token) {
        if (tokenService.canGet(token)) {
            var user = tokenService.getUser(token);
            cartService.buy(user.username());
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/cart/{productType}/{productId}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable String productType, @PathVariable int productId) {
        if (tokenService.canGet(token)) {
            var user = tokenService.getUser(token);
            cartRepository.delete(user.username(), productType, productId);
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
