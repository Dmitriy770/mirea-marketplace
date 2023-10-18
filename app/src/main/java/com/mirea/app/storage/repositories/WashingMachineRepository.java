package com.mirea.app.storage.repositories;

import com.mirea.app.models.WashingMachineModel;
import com.mirea.app.storage.mappers.WashingMachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WashingMachineRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WashingMachineRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(WashingMachineModel washingMachine) {
        jdbcTemplate.update("""
                        INSERT INTO washing_machine(producer, tank_volume, seller_username, product_type, price, name)
                        VALUES (?, ?, ?, ?, ?, ?)
                        """,
                washingMachine.producer(),
                washingMachine.tankVolume(),
                washingMachine.sellerUsername(),
                washingMachine.productType(),
                washingMachine.price(),
                washingMachine.name());
    }

    public void update(WashingMachineModel washingMachine) {
        jdbcTemplate.update("""
                        UPDATE washing_machine
                        SET producer=?, tank_volume=?, seller_username=?, product_type=?, price=?, name=?
                        WHERE id=?
                        """,
                washingMachine.producer(),
                washingMachine.tankVolume(),
                washingMachine.sellerUsername(),
                washingMachine.productType(),
                washingMachine.price(),
                washingMachine.name(),
                washingMachine.id());
    }

    public void delete(int id) {
        jdbcTemplate.update("""
                DELETE FROM washing_machine
                WHERE id=?
                """, id);
    }

    public WashingMachineModel getById(int id) {
        return jdbcTemplate.query("""
                                SELECT id, producer, tank_volume, seller_username, product_type, price, name
                                FROM washing_machine
                                WHERE id = ?
                                """,
                        new Object[]{id},
                        new WashingMachineMapper())
                .stream().findAny().orElseThrow();
    }

    public List<WashingMachineModel> getAll() {
        return jdbcTemplate.query("""
                        SELECT id, producer, tank_volume, seller_username, product_type, price, name
                        FROM washing_machine
                        """,
                new WashingMachineMapper());
    }
}
