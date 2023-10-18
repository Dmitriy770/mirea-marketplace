package com.mirea.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirea.app.models.UserModel;
import com.mirea.app.storage.repositories.BookRepository;
import com.mirea.app.storage.repositories.TelephoneRepository;
import com.mirea.app.storage.repositories.WashingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {
    private final BookRepository bookRepository;
    private final TelephoneRepository telephoneRepository;
    private final WashingMachineRepository washingMachineRepository;

    @Autowired
    public TokenService(BookRepository bookRepository, TelephoneRepository telephoneRepository, WashingMachineRepository washingMachineRepository) {
        this.bookRepository = bookRepository;
        this.telephoneRepository = telephoneRepository;
        this.washingMachineRepository = washingMachineRepository;
    }

    public UserModel getUser(String token) {
        token = token.replaceFirst("Bearer ", "");
        var chunks = token.split("\\.");

        var decoder = Base64.getUrlDecoder();
        var payload = new String(decoder.decode(chunks[1]));

        var objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(payload, UserModel.class);
        } catch (Exception e) {
            return new UserModel("", new String[]{});
        }
    }

    public boolean canGet(String token) {
        var user = getUser(token);
        return user.hasRole("ROLE_USER") || user.hasRole("ROLE_SELLER") || user.hasRole("ROLE_ADMINISTRATOR");
    }

    public boolean canUpdateProduct(String token, String productType, int productId) {
        var user = getUser(token);

        if (user.hasRole("ROLE_ADMINISTRATOR")) {
            return true;
        }

        if (user.hasRole("ROLE_SELLER")) {
            switch (productType) {
                case "BOOK" -> {
                    var book = bookRepository.getById(productId);
                    return book.sellerUsername().equals(user.username());
                }
                case "TELEPHONE" -> {
                    var telephone = telephoneRepository.getById(productId);
                    return telephone.sellerUsername().equals(user.username());
                }
                case "WASHING_MACHINE" -> {
                    var washingMachine = washingMachineRepository.getById(productId);
                    return washingMachine.sellerUsername().equals(user.username());
                }
            }
        }
        return false;
    }
}
