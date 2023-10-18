package com.mirea.app.storage.repositories;

import com.mirea.app.models.BookModel;
import com.mirea.app.storage.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(BookModel book) {
        jdbcTemplate.update("""
                        INSERT INTO book (title, author, product_type, seller_username, price)
                        VALUES (?, ?, ?, ?, ?)
                        """,
                book.title(), book.author(), book.productType(), book.sellerUsername(), book.price());
    }

    public void update(BookModel book) {
        jdbcTemplate.update("""
                        UPDATE book
                        SET title=?, author=?, product_type=?, seller_username=?, price=?
                        WHERE id=?
                        """,
                book.title(), book.author(), book.productType(), book.sellerUsername(), book.price(), book.id());
    }

    public void delete(int id) {
        jdbcTemplate.update("""
                DELETE FROM book
                WHERE id=?
                """, id);
    }

    public BookModel getById(int id) {
        return jdbcTemplate.query("""
                                SELECT id, title, author, price, seller_username, product_type
                                FROM book
                                WHERE id = ?
                                """,
                        new Object[]{id},
                        new BookMapper())
                .stream().findAny().orElseThrow();
    }

    public List<BookModel> getAll() {
        return jdbcTemplate.query("""
                        SELECT id, title, author, price, seller_username, product_type
                        FROM book
                        """,
                new BookMapper());
    }
}
