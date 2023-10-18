package com.mirea.app.controllers;

import com.mirea.app.models.WashingMachineModel;
import com.mirea.app.services.TokenService;
import com.mirea.app.storage.repositories.WashingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WashingMachineController {
    private final WashingMachineRepository washingMachineRepository;
    private final TokenService tokenService;

    @Autowired
    public WashingMachineController(WashingMachineRepository washingMachineRepository, TokenService tokenService) {
        this.washingMachineRepository = washingMachineRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/washing-machine")
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody WashingMachineModel washingMachine) {
        var user = tokenService.getUser(token);
        if (user.hasRole("ROLE_SELLER") || user.hasRole("ROLE_ADMINISTRATOR")) {
            washingMachineRepository.add(new WashingMachineModel(
                    null,
                    washingMachine.producer(),
                    washingMachine.tankVolume(),
                    user.username(),
                    "WASHING_MACHINE",
                    washingMachine.price(),
                    washingMachine.name()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/washing-machine")
    public ResponseEntity<List<WashingMachineModel>> get(@RequestHeader("Authorization") String token) {
        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(washingMachineRepository.getAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/washing-machine/{id}")
    public ResponseEntity<WashingMachineModel> getById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(washingMachineRepository.getById(id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/washing-machine")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String token, @RequestBody WashingMachineModel washingMachine) {
        if (tokenService.canUpdateProduct(token, "WASHING_MACHINE", washingMachine.id())) {
            var user = tokenService.getUser(token);
            washingMachineRepository.update(new WashingMachineModel(
                    washingMachine.id(),
                    washingMachine.producer(),
                    washingMachine.tankVolume(),
                    user.username(),
                    "WASHING_MACHINE",
                    washingMachine.price(),
                    washingMachine.name()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/washing-machine/{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable int id) {
        if (tokenService.canUpdateProduct(token, "WASHING_MACHINE", id)) {
            washingMachineRepository.delete(id);
            return ResponseEntity.ok("{\"status\": \"OK\"}");

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
