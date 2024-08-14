package com.example.demo.dao;

import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
public class PublisherDAO implements DAO<Publisher>{
    private static final Logger logger = LoggerFactory.getLogger(PublisherDAO.class);
    private JdbcTemplate jdbcTemplate;

    public PublisherDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    RowMapper<Publisher> rowMapper = (rs, rowNum) -> {
        Publisher publisher = new Publisher();
        publisher.setPublisherID(rs.getInt("publisherid"));
        publisher.setWebsite(rs.getString("website"));
        publisher.setAddress(rs.getString("address"));
        publisher.setName(rs.getString("name"));
        return publisher;
    };

    @Override
    public Publisher get(int id) {
        String sql = "SELECT * FROM Publishers where publisherID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
    }


    @Override
    public int create(Publisher publisher) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Publisher> list() {
        return null;
    }

    @Override
    public int update(Publisher t1, int id) {
        return 0;
    }
}
