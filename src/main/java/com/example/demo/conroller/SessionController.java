package com.example.demo.conroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/session")
public class SessionController {


    private SessionManager sessionManager;

    public SessionController(HttpSession httpSession){
        this.sessionManager = new SessionManager(httpSession);
    }
    @GetMapping("/set")
    public String setSessionAttribute(String username) {
        sessionManager.setAttribute("username", username);
        return "Session attribute set!";
    }

    @GetMapping("/get")
    public String getSessionAttribute() {
        String username = (String) sessionManager.getAttribute("username");
        return "Session attribute value: " + username;
    }

    @GetMapping("/invalidate")
    public String invalidateSession() {
        sessionManager.invalidateSession();
        return "Session invalidated!";
    }
}