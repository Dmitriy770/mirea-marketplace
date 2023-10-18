package com.mirea.authorization.controllers;

import com.mirea.authorization.models.UserModel;
import com.mirea.authorization.responses.UpdateRolesResponse;
import com.mirea.authorization.services.TokenService;
import com.mirea.authorization.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final TokenService tokenService;

    public AdminController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserModel[]> getUsers(@RequestHeader("Authorization") String token) {
        var user = tokenService.getUser(token);
        if (user.hasRole("ROLE_ADMINISTRATOR")) {
            return ResponseEntity.ok(userService.getUsers());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/users/update-roles")
    public ResponseEntity<String> updateRoles(@RequestHeader("Authorization") String token, @RequestBody UpdateRolesResponse response) {
        var user = tokenService.getUser(token);
        if (user.hasRole("ROLE_ADMINISTRATOR")) {
            userService.updateRoles(new UserModel(
                    response.username(),
                    null,
                    response.roles()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
