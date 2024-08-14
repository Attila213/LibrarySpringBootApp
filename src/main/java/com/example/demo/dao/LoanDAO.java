package com.example.demo.dao;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Loan;
import com.example.demo.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
public class LoanDAO implements DAO<Loan>{

    private static final Logger logger = LoggerFactory.getLogger(LoanDAO.class);
    private JdbcTemplate jdbcTemplate;

    BookDAO bookDAO;
    MemberDAO memberDAO;
    public LoanDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        bookDAO = new BookDAO(jdbcTemplate);
        memberDAO = new MemberDAO(jdbcTemplate);
    }

    RowMapper<Loan> rowMapper = (rs, rowNum) -> {

        Loan loan = new Loan();
        loan.setLoanID(rs.getInt("loanid"));
        loan.setLoanDate(rs.getString("loan_date"));
        loan.setDueDate(rs.getString("due_date"));
        loan.setReturnDate(rs.getString("return_date"));

        Book book = bookDAO.get(rs.getInt("bookid"));
        loan.setBook(book);

        Member member = memberDAO.get(rs.getInt("memberid"));
        loan.setMember(member);

        return loan;
    };

    @Override
    public Loan get(int id) {
        String sql = "SELECT * FROM loans where loanid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
    }

    @Override
    public List<Loan> list() {
        return null;
    }

    @Override
    public int create(Loan loan) {
        String sql = "INSERT INTO loans(due_date ,loan_date,return_date,bookid,memberid) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql,loan.getDueDate(),loan.getLoanDate(),loan.getReturnDate(),loan.getBook().getBookID(),loan.getMember().getMemberID());
    }


    @Override
    public int update(Loan t1, int id) {
        return 0;
    }

    @Override
    public void delete(int id) {
        String sql = "Delete from loans where loanid = ?";
        jdbcTemplate.update(sql,id);
    }

    public void delete(int bookid, int memberid) {
        String sql = "Delete from loans where bookid = ? and memberid = ?";
        jdbcTemplate.update(sql,bookid,memberid);
    }


}
