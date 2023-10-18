package com.mirea.app.controllers;

import com.mirea.app.models.TelephoneModel;
import com.mirea.app.services.TokenService;
import com.mirea.app.storage.repositories.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TelephoneController {
    private final TelephoneRepository telephoneRepository;
    private final TokenService tokenService;

    @Autowired
    public TelephoneController(TelephoneRepository telephoneRepository, TokenService tokenService) {
        this.telephoneRepository = telephoneRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/telephone")
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody TelephoneModel telephone) {
        var user = tokenService.getUser(token);
        if (user.hasRole("ROLE_SELLER") || user.hasRole("ROLE_ADMINISTRATOR")) {
            telephoneRepository.add(new TelephoneModel(
                    null,
                    telephone.batteryCapacity(),
                    telephone.price(),
                    user.username(),
                    "TELEPHONE",
                    telephone.name(),
                    telephone.producer()
            ));

            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/telephone")
    public ResponseEntity<List<TelephoneModel>> get(@RequestHeader("Authorization") String token) {
        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(telephoneRepository.getAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/telephone/{id}")
    public ResponseEntity<TelephoneModel> getById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(telephoneRepository.getById(id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/telephone")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String token, @RequestBody TelephoneModel telephone) {
        if (tokenService.canUpdateProduct(token, "TELEPHONE", telephone.id())) {
            var user = tokenService.getUser(token);
            telephoneRepository.update(new TelephoneModel(
                    telephone.id(),
                    telephone.batteryCapacity(),
                    telephone.price(),
                    user.username(),
                    "TELEPHONE",
                    telephone.name(),
                    telephone.producer()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/telephone/{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable int id) {
        if (tokenService.canUpdateProduct(token, "TELEPHONE", id)) {
            telephoneRepository.delete(id);
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
