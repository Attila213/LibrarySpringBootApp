package com.example.demo.conroller;

import ch.qos.logback.core.model.Model;

import com.example.demo.dao.MemberDAO;
import com.example.demo.model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {

    private MemberDAO memberDAO;
    public LoginController(MemberDAO dao){
        this.memberDAO = dao;
    }
    @GetMapping("/login")
    public ModelAndView redirectLogin(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @GetMapping("/register")
    public ModelAndView redirectRegister(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/loginProcess")
    @ResponseBody
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        String encryptedPassword = Sha512Encoder.encode(password);


        if (memberDAO.getEmailPassPair(email,encryptedPassword)) {
            session.setAttribute("email", email);
            session.setAttribute("id",memberDAO.getMemberByEmail(email).getMemberID() );

            return "{\"status\": \"success\", \"message\": \"Login successful.\"}";
        } else {


            return "{\"status\": \"error\", \"message\": \"Invalid credentials.\"}";
        }
    }

    @PostMapping("/registerProcess")
    @ResponseBody
    public String register(@RequestParam("email") String email,@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname,@RequestParam("address") String address,@RequestParam("phone") String phone, @RequestParam("password") String password, HttpSession session) throws NoSuchAlgorithmException {

        if(memberDAO.isEmailExists(email)){
            return "{\"status\": \"error\", \"message\": \"Email is taken.\"}";
        }
        Member member = new Member();
        member.setFirstName(firstname);
        member.setLastName(lastname);
        member.setEmail(email);
        member.setAddress(address);
        member.setPhone(phone);
        String encryptedPassword = Sha512Encoder.encode(password);
        member.setPassword(encryptedPassword);

        memberDAO.create(member);


        return "{\"status\": \"success\", \"message\": \"Sign up successful.\"}";
    }



    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/login");
    }
}


