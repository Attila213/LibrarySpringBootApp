package com.example.demo.dao;

import com.example.demo.model.Author;
import com.example.demo.model.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
public class AuthorsDAO implements DAO<Author> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorsDAO.class);
    private JdbcTemplate jdbcTemplate;

    public AuthorsDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Author> rowMapper = (rs, rowNum) -> {

        Author author = new Author();
        author.setBiography(rs.getString("Biography"));
        author.setAuthorID(rs.getInt("authorid"));
        author.setLastName(rs.getString("last_name"));
        author.setFirstName(rs.getString("first_name"));
        author.setDateOfBirth(rs.getDate("date_of_birth"));

        return author;
    };

    @Override
    public Author get(int id) {
        String sql = "SELECT * FROM authors where authorid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
    }

    @Override
    public int create(Author author) {
        return 0;
    }

    @Override
    public int update(Author t1, int id) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Author> list() {
        return null;
    }
}

