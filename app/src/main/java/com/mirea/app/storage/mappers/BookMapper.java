package com.mirea.app.storage.mappers;

import com.mirea.app.models.BookModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<BookModel> {
    @Override
    public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BookModel(
                rs.getInt("id"),
                rs.getString("author"),
                rs.getString("seller_username"),
                rs.getString("product_type"),
                rs.getDouble("price"),
                rs.getString("title")
        );
    }
}
