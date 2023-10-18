package com.mirea.app.models;

public record UserModel(
        String username,
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
