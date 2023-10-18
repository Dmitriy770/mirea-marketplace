package com.mirea.app.storage.mappers;

import com.mirea.app.models.StorageModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageMapper implements RowMapper<StorageModel> {
    @Override
    public StorageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StorageModel(
                rs.getString("product_type"),
                rs.getInt("product_id"),
                rs.getInt("amount")
        );
    }
}
