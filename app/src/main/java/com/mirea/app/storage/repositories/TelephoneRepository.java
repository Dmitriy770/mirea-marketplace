package com.mirea.app.storage.repositories;

import com.mirea.app.models.TelephoneModel;
import com.mirea.app.storage.mappers.TelephoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelephoneRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TelephoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(TelephoneModel telephone) {
        jdbcTemplate.update("""
                        INSERT INTO telephone (producer, battery_capacity, seller_username, product_type, price, name)
                        VALUES (?, ?, ?, ?, ?, ?)
                        """,
                telephone.producer(),
                telephone.batteryCapacity(),
                telephone.sellerUsername(),
                telephone.productType(),
                telephone.price(),
                telephone.name());
    }

    public void update(TelephoneModel telephone) {
        jdbcTemplate.update("""
                        UPDATE telephone
                        SET producer=?, battery_capacity=?, seller_username=?, product_type=?, price=?, name=?
                        WHERE id=?
                        """,
                telephone.producer(),
                telephone.batteryCapacity(),
                telephone.sellerUsername(),
                telephone.productType(),
                telephone.price(),
                telephone.name(),
                telephone.id());
    }

    public void delete(int id) {
        jdbcTemplate.update("""
                DELETE FROM telephone
                WHERE id=?
                """, id);
    }

    public TelephoneModel getById(int id) {
        return jdbcTemplate.query("""
                                SELECT id, producer, battery_capacity, seller_username, product_type, price, name
                                FROM telephone
                                WHERE id = ?
                                """,
                        new Object[]{id},
                        new TelephoneMapper())
                .stream().findAny().orElseThrow();
    }

    public List<TelephoneModel> getAll() {
        return jdbcTemplate.query("""
                        SELECT id, producer, battery_capacity, seller_username, product_type, price, name
                        FROM telephone
                        """,
                new TelephoneMapper());
    }
}
