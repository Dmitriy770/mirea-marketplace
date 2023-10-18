package com.mirea.app.storage.repositories;

import com.mirea.app.models.StorageModel;
import com.mirea.app.storage.mappers.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StorageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StorageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(StorageModel model) {
        jdbcTemplate.update("""
                        INSERT INTO storage (product_type, product_id, amount)
                        VALUES (?, ?, ?)
                        """,
                model.productType(), model.productId(), model.amount());
    }

    public void updateAmount(StorageModel model) {
        jdbcTemplate.update("""
                        UPDATE storage
                        SET amount=?
                        WHERE product_type=? and product_id=?
                        """,
                model.amount(), model.productType(), model.productId());
    }

    public void delete(String productType, int productId) {
        jdbcTemplate.update("""
                DELETE FROM storage
                WHERE product_type=? and product_id=?
                """, productType, productId);
    }

    public StorageModel get(String productType, int productId) {
        return jdbcTemplate.query("""
                                SELECT product_type, product_id, amount
                                FROM storage
                                WHERE product_type=? and product_id=?
                                """,
                        new Object[]{productType, productId},
                        new StorageMapper())
                .stream().findAny().orElse(null);
    }

    public List<StorageModel> getAll() {
        return jdbcTemplate.query("""
                        SELECT product_type, product_id, amount
                        FROM storage
                        """,
                new StorageMapper());
    }
}
