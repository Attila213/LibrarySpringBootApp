package com.example.demo.conroller;

import ch.qos.logback.core.model.Model;
import com.example.demo.model.Loan;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/")
public class DashboardContoller {
    @GetMapping("/")
    public RedirectView redirect(Model model, HttpSession session) {

        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new RedirectView("/login");
        }
        return new RedirectView("/dashboard");
    }
    @GetMapping("/dashboard")
    public ModelAndView dashboard(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");

        if(session.getAttribute("email") == null){
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");

        if(session.getAttribute("email") == null){
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }


}
