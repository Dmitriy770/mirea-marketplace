package com.mirea.app.storage.repositories;

import com.mirea.app.models.CartModel;
import com.mirea.app.storage.mappers.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(CartModel model) {
        jdbcTemplate.update("""
                        INSERT INTO cart (username, product_type, product_id, product_amount)
                        VALUES (?, ?, ?, ?)
                        """,
                model.username(), model.productType(), model.productId(), model.amount());
    }

    public void updateAmount(CartModel model) {
        jdbcTemplate.update("""
                        UPDATE cart
                        SET product_amount=?
                        WHERE username=? and product_type=? and product_id=?
                        """,
                model.amount(), model.username(), model.productType(), model.productId());
    }

    public void delete(String username, String productType, int productId) {
        jdbcTemplate.update("""
                DELETE FROM cart
                WHERE username=? and product_type=? and product_id=?
                """, username, productType, productId);
    }

    public CartModel get(String username, String productType, int productId) {
        return jdbcTemplate.query("""
                                SELECT user_id, product_type, product_id, product_amount
                                FROM cart
                                WHERE username=? and product_type=? and product_id=?
                                """,
                        new Object[]{username, productType, productId},
                        new CartMapper())
                .stream().findAny().orElse(new CartModel(username, productType, productId, 0));
    }

    public List<CartModel> getClientCart(String username) {
        return jdbcTemplate.query("""
                        SELECT username, product_type, product_id, product_amount
                        FROM cart
                        WHERE username=?
                        """,
                new Object[]{username},
                new CartMapper());
    }
}
