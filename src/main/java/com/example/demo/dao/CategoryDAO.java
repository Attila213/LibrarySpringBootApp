package com.example.demo.dao;

import com.example.demo.model.Author;
import com.example.demo.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
public class CategoryDAO implements DAO<Category>{

    private static final Logger logger = LoggerFactory.getLogger(CategoryDAO.class);
    private JdbcTemplate jdbcTemplate;

    public CategoryDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Category> rowMapper = (rs, rowNum) -> {

        Category category = new Category();
        category.setCategoryName(rs.getString("category_name"));
        category.setCategoryID(rs.getInt("categoryid"));

        return category;
    };
    @Override
    public Category get(int id) {
        String sql = "SELECT * FROM Categories where categoryid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
    }

    @Override
    public List<Category> list() {
        return null;
    }

    @Override
    public int create(Category category) {
        return 0;
    }

    @Override
    public int update(Category t1, int id) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}
