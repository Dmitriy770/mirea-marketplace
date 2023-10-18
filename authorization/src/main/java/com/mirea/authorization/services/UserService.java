package com.mirea.authorization.services;

import com.mirea.authorization.entities.RoleEntity;
import com.mirea.authorization.entities.UserEntity;
import com.mirea.authorization.models.UserModel;
import com.mirea.authorization.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyPair;
import io.jsonwebtoken.security.RsaPublicJwk;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final javax.crypto.SecretKey secureKey = Jwts.SIG.HS256.key().build();

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        var roles = new HashSet<RoleEntity>();
        roles.add(new RoleEntity("ROLE_ADMINISTRATOR"));
        var user = new UserEntity("admin", bCryptPasswordEncoder.encode("password"));
        user.setRoles(roles);

        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findById(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return user.get();
    }

    public boolean createUser(UserModel user) {
        var userFromDB = userRepository.findById(user.username());

        if (userFromDB.isPresent()) {
            return false;
        }

        var userEntity = new UserEntity(
                user.username(),
                bCryptPasswordEncoder.encode(user.password())
        );

        var roles = new HashSet<RoleEntity>();
        roles.add(new RoleEntity("ROLE_USER"));
        userEntity.setRoles(roles);

        userRepository.save(userEntity);

        return true;
    }

    public String getToken(UserModel user) {

        var userFromDB = userRepository.findById(user.username());

        if (userFromDB.isEmpty()) {
            return "";
        }

        if (!bCryptPasswordEncoder.matches(user.password(), userFromDB.get().getPassword())) {
            return "";
        }

        var token = Jwts.builder()
                .claim("username", userFromDB.get().getUsername())
                .claim("roles", userFromDB.get().getRoles().stream().map(RoleEntity::getName).toArray())
                .signWith(secureKey)
                .compact();

        return token;
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secureKey).build().parseSignedClaims(token);
            return true;

        } catch (JwtException e) {
            return false;
        }
    }

    public UserModel[] getUsers() {
        var users = userRepository.findAll();
        var usersEntity = new ArrayList<UserEntity>();
        for (var user : users) {
            usersEntity.add(user);
        }

        return usersEntity.stream().map(u -> new UserModel(
                u.getUsername(),
                u.getPassword(),
                u.getRoles().stream().map(RoleEntity::getName).toArray(String[]::new)
        )).toArray(UserModel[]::new);
    }

    public void updateRoles(UserModel user) {
        var userFromDB = userRepository.findById(user.username());

        if (userFromDB.isEmpty()) {
            return;
        }
        var roles = new HashSet<RoleEntity>();
        for (var role : user.roles()) {
            roles.add(new RoleEntity(role));
        }

        var newUserEntity = new UserEntity(
                user.username(),
                userFromDB.get().getPassword()
        );
        newUserEntity.setRoles(roles);

        userRepository.save(newUserEntity);
    }
}
