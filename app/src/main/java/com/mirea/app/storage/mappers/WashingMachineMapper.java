package com.mirea.app.storage.mappers;

import com.mirea.app.models.WashingMachineModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WashingMachineMapper implements RowMapper<WashingMachineModel> {

    @Override
    public WashingMachineModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WashingMachineModel(
                rs.getInt("id"),
                rs.getString("producer"),
                rs.getDouble("tank_volume"),
                rs.getString("seller_username"),
                rs.getString("product_type"),
                rs.getDouble("price"),
                rs.getString("name")
        );
    }
}
