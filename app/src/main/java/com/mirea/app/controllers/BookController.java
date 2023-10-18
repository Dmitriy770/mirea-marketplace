package com.mirea.app.controllers;

import com.mirea.app.models.BookModel;
import com.mirea.app.services.TokenService;
import com.mirea.app.storage.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final TokenService tokenService;

    @Autowired
    public BookController(BookRepository bookRepository, TokenService tokenService) {
        this.bookRepository = bookRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody BookModel book) {

        var user = tokenService.getUser(token);
        if (user.hasRole("ROLE_SELLER") || user.hasRole("ROLE_ADMINISTRATOR")) {
            bookRepository.add(new BookModel(
                    null,
                    book.author(),
                    user.username(),
                    "BOOK",
                    book.price(),
                    book.title()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookModel>> get(@RequestHeader("Authorization") String token) {

        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(bookRepository.getAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookModel> getById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        if (tokenService.canGet(token)) {
            return ResponseEntity.ok(bookRepository.getById(id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PutMapping("/book")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String token, @RequestBody BookModel book) {
        if (tokenService.canUpdateProduct(token, "BOOK", book.id())) {
            var user = tokenService.getUser(token);
            bookRepository.update(new BookModel(
                    book.id(),
                    book.author(),
                    user.username(),
                    "BOOK",
                    book.price(),
                    book.title()
            ));
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable int id) {
        if (tokenService.canUpdateProduct(token, "BOOK", id)) {
            bookRepository.delete(id);
            return ResponseEntity.ok("{\"status\": \"OK\"}");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
