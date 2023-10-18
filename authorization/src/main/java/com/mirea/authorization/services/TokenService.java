package com.mirea.authorization.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirea.authorization.models.UserModel;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {

    public UserModel getUser(String token) {
        token = token.replaceFirst("Bearer ", "");
        var chunks = token.split("\\.");

        var decoder = Base64.getUrlDecoder();
        var payload = new String(decoder.decode(chunks[1]));

        var objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(payload, UserModel.class);
        } catch (Exception e) {
            return new UserModel("", "", new String[]{});
        }
    }
}
