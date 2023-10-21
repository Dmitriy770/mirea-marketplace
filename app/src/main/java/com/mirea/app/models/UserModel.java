package com.mirea.app.models;

import java.util.Arrays;

public record UserModel(
        String username,
        String[] roles
) {
    public boolean hasRole(String role) {
        return Arrays.asList(roles).contains(role);
    }
}
