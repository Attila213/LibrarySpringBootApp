package com.example.demo.dao;
import com.example.demo.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class MemberDAO implements DAO<Member>{

    private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);
    private JdbcTemplate jdbcTemplate;

    public MemberDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Member> rowMapper=(rs, rowNum) -> {
        Member member = new Member();
        member.setMemberID(rs.getInt("memberID"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setEmail(rs.getString("email"));
        member.setPhone(rs.getString("phone"));
        member.setAddress(rs.getString("address"));
        member.setJoinDate(rs.getDate("join_date"));
        member.setPassword(rs.getString("password"));

        return member;
    };

    @Override
    public Member get(int id) {
        String sql = "SELECT * FROM Members where Memberid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
    }


    public Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM Members where email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email},rowMapper);
    }

    public boolean getEmailPassPair(String email, String pass) {
        String sql = "SELECT COUNT(*) FROM Members WHERE email = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email, pass}, Integer.class);
        return count != null && count > 0;
    }

    public int getIdByEmail(String email, String pass) {
        String sql = "SELECT id FROM Members WHERE email";
        Integer id = jdbcTemplate.queryForObject(sql, new Object[]{email, pass}, Integer.class);
        return id;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Members WHERE email = ? ";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }


    @Override
    public int create(Member member) {
        String sql = "INSERT INTO MEMBERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE, ADDRESS, JOIN_DATE, PASSWORD) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
        return jdbcTemplate.update(sql,
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getPassword());
    }

    @Override
    public int update(Member t1, int id) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Member> list() {
        return null;
    }
}
