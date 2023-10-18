package com.mirea.app.storage.mappers;

import com.mirea.app.models.TelephoneModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TelephoneMapper implements RowMapper<TelephoneModel> {
    @Override
    public TelephoneModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TelephoneModel(
                rs.getInt("id"),
                rs.getDouble("battery_capacity"),
                rs.getDouble("price"),
                rs.getString("seller_username"),
                rs.getString("product_type"),
                rs.getString("name"),
                rs.getString("producer")
        );
    }
}
