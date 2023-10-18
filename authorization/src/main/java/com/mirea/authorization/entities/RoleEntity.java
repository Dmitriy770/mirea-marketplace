package com.mirea.authorization.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("Role")
public class RoleEntity implements GrantedAuthority, Serializable {
    private String name;

    public RoleEntity(String name)
    {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
