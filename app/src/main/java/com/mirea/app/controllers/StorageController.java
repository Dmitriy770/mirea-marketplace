package com.mirea.app.controllers;

import com.mirea.app.models.StorageModel;
import com.mirea.app.services.StorageService;
import com.mirea.app.services.TokenService;
import com.mirea.app.storage.repositories.StorageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StorageController {
    private final StorageRepository storageRepository;
    private final StorageService storageService;
    private final TokenService tokenService;

    public StorageController(StorageRepository storageRepository, StorageService storageService, TokenService tokenService) {
        this.storageRepository = storageRepository;
        this.storageService = storageService;
        this.tokenService = tokenService;
    }

    @PostMapping("/storage")
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody StorageModel model) {
        if (tokenService.canUpdateProduct(token, model.productType(), model.productId())) {
            storageService.addProduct(model);
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/storage")
    public ResponseEntity<List<StorageModel>> get(@RequestHeader("Authorization") String token) {
        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(storageRepository.getAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/storage")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String token, @RequestBody StorageModel model) {
        if (tokenService.canUpdateProduct(token, model.productType(), model.productId())) {
            storageRepository.updateAmount(model);
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/storage")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @RequestBody StorageModel model) {
        if (tokenService.canUpdateProduct(token, model.productType(), model.productId())) {
            storageRepository.delete(model.productType(), model.productId());
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
