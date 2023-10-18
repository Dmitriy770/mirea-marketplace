package com.mirea.authorization.models;

public record UserModel(
        String username,
        String password,
        String[] roles
) {
    public boolean hasRole(String role) {
        for (var r : roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
