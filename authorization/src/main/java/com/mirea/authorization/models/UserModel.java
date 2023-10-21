package com.mirea.authorization.models;

import java.util.Arrays;

public record UserModel(
        String username,
        String password,
        String[] roles
) {
    public boolean hasRole(String role) {
        return Arrays.asList(roles).contains(role);
    }
}
