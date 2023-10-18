package com.mirea.authorization.responses;

public record UpdateRolesResponse(
        String username,
        String[] roles
) {

}
