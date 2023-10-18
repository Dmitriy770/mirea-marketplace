package com.mirea.app.storage.mappers;

import com.mirea.app.models.CartModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper implements RowMapper<CartModel> {
    @Override
    public CartModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CartModel(
                rs.getString("username"),
                rs.getString("product_type"),
                rs.getInt("product_id"),
                rs.getInt("product_amount")
        );
    }
}
