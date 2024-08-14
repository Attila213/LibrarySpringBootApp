package com.example.demo.conroller;
import ch.qos.logback.core.model.Model;
import com.example.demo.dao.BookDAO;
import com.example.demo.dao.DAO;
import com.example.demo.model.Book;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/")
public class BookContoller {

    private BookDAO bookDAO;
    public BookContoller(BookDAO dao){
        this.bookDAO = dao;
    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> listModels(Model model,HttpSession session) {
       List<Book> books = bookDAO.listNotLoaned(session);
       return ResponseEntity.ok(books);
    }
    @GetMapping("/loanedbooks")
    public ResponseEntity<List<Book>> listLoanedBooks(Model model,HttpSession session) {
        List<Book> books = bookDAO.listLoaned(session);
        return ResponseEntity.ok(books);
    }



    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBooksDetail(@PathVariable int id) {
        Book book = bookDAO.get(id);
        return ResponseEntity.ok(book);

    }

    @GetMapping("/books/create")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        int result = bookDAO.create(book);
        if(result >0){
            return ResponseEntity.status(HttpStatus.CREATED).body("Book inserted successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert the book.");
    }

    @GetMapping("/books/{id}/update")
    public ResponseEntity<String> updateBook(@PathVariable("id") int id,@RequestBody Book book) {
        bookDAO.update(book,id);
        return ResponseEntity.ok("Book updated");
    }

    @GetMapping("/books/{id}/delete")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return ResponseEntity.ok("Book updated");
    }
}
