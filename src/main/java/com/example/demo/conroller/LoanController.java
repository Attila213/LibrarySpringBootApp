    package com.example.demo.conroller;

    import ch.qos.logback.core.model.Model;
    import com.example.demo.dao.BookDAO;
    import com.example.demo.dao.DAO;
    import com.example.demo.dao.LoanDAO;
    import com.example.demo.dao.MemberDAO;
    import com.example.demo.model.Book;
    import com.example.demo.model.Loan;
    import com.example.demo.model.Member;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.ModelAndView;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.time.LocalDate;
    import java.util.Date;
    import java.util.Map;
    @Controller
    @RequestMapping("/loan/")
    public class LoanController {

        private MemberDAO memberDAO;
        private BookDAO bookDAO;
        private LoanDAO loanDAO;

        public LoanController(MemberDAO memberDao, BookDAO bookDao, LoanDAO loanDao) {
            this.memberDAO = memberDao;
            this.bookDAO = bookDao;
            this.loanDAO = loanDao;
        }

        @PostMapping("/loanProcess")
        public ResponseEntity<String> loanBook(HttpSession session, @RequestBody Map<String, String> loanData) {
            String bookIdString = loanData.get("bookid");
            int bookId = Integer.parseInt(bookIdString);

            Loan loan = new Loan();
            loan.setBook(bookDAO.get(bookId));

            String startDateString = LocalDate.now().toString(); // Assuming you want the current date as start date
            String dueDateString = LocalDate.now().plusMonths(2).toString(); // Example: due date is 2 weeks from now

            loan.setLoanDate(startDateString);
            loan.setReturnDate(null);
            loan.setDueDate(dueDateString);

            int memberId = (int) session.getAttribute("id");
            loan.setMember(memberDAO.get(memberId));

            loanDAO.create(loan);

            return ResponseEntity.ok("Loan successful");
        }

        @PostMapping("/deleteLoan")
        public ResponseEntity<String> DeleteLoanedBook(HttpSession session, @RequestBody Map<String, String> loanData) {

            int memberId = (int) session.getAttribute("id");
            String bookIdString = loanData.get("bookid");
            int bookId = Integer.parseInt(bookIdString);

            loanDAO.delete(bookId,memberId);


            return ResponseEntity.ok("Return successful");
        }
    }
