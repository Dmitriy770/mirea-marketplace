package com.mirea.authorization.controllers;

import com.mirea.authorization.models.UserModel;
import com.mirea.authorization.requests.LoginRequest;
import com.mirea.authorization.requests.RegistrationRequest;
import com.mirea.authorization.responses.LoginResponse;
import com.mirea.authorization.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        var token = userService.getToken(new UserModel(
                request.username(),
                request.password(),
                null
        ));

        return new LoginResponse(token);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest request) {
        if (userService.createUser(new UserModel(
                request.username(),
                request.password(),
                null
        ))) {
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
