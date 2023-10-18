package com.mirea.authorization.requests;


public record LoginRequest(
        String username,
        String password
) {
}
