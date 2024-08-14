package com.example.demo.dao;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.model.Publisher;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookDAO implements DAO<Book>{


    RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setBookID(rs.getInt("bookid"));
        book.setTitle(rs.getString("title"));
        book.setIsbn(rs.getString("ISBN"));
        book.setPages(rs.getInt("pages"));
        book.setPublicationYear(rs.getInt("Publication_Year"));

        Publisher publisher = publisherDAO.get(rs.getInt("publisherid"));
        book.setPublisher(publisher);

        Author author = authorsDAO.get(rs.getInt("authorid"));
        book.setAuthor(author);

        Category category = categoryDAO.get(rs.getInt("categoryid"));
        book.setCategory(category);

        return book;
    };

    private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);
    private JdbcTemplate jdbcTemplate;
    private static PublisherDAO publisherDAO;
    private static AuthorsDAO authorsDAO;
    private static CategoryDAO categoryDAO;



    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.publisherDAO = new PublisherDAO(jdbcTemplate);
        this.authorsDAO = new AuthorsDAO(jdbcTemplate);
        this.categoryDAO = new CategoryDAO(jdbcTemplate);

    }

    @Override
    public Book get(int id) {
        String sql = "SELECT * FROM BOOKS where BookID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
    }

    @Override
    public List<Book> list() {
        String sql = "select * from books where books.bookid < 100";
        return jdbcTemplate.query(sql,rowMapper);
    }

    public List<Book> listNotLoaned(HttpSession session) {
        int id = (int) session.getAttribute("id");
        String sql = "SELECT * FROM books WHERE books.bookid < 100 AND bookid NOT IN (SELECT bookid FROM Loans WHERE memberid = ?)";

        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
    }

    public List<Book> listLoaned(HttpSession session) {
        int id = (int) session.getAttribute("id");
        String sql = "SELECT * FROM books WHERE bookid IN (SELECT bookid FROM Loans WHERE memberid = ?)";

        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
    }


    @Override
    public int create(Book book) {
        String sql = "INSERT INTO Books (Title, ISBN, Publication_year, Pages, PublisherID) VALUES (?, ?, ?, ?, ?);";
        return jdbcTemplate.update(
                sql,
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getPages(),
                book.getPublisher().getPublisherID()  // Assuming getPublisher() is not null
        );
    }

    @Override
    public int update(Book book, int id) {
        String sql = "UPDATE Books BookID = ?, Title = ?, ISBN = ?, Publication_year  = ?, Pages = ?, PublisherID = ? Where bookid = ?";
        return jdbcTemplate.update(sql, book.getBookID(), book.getTitle(), book.getIsbn(), book.getPublicationYear(),book.getPages(),book.getPublisher().getPublisherID(),id);
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM Books WHERE bookID = ?";
        jdbcTemplate.update(sql,id);
    }
}
