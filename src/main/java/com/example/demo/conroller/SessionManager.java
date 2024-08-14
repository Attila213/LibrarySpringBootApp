package com.example.demo.conroller;

import jakarta.servlet.http.HttpSession;

public class SessionManager {

    private final HttpSession httpSession;

    public SessionManager(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    // Set a session attribute
    public void setAttribute(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    // Get a session attribute
    public Object getAttribute(String key) {
        return httpSession.getAttribute(key);
    }

    // Remove a session attribute
    public void removeAttribute(String key) {
        httpSession.removeAttribute(key);
    }

    // Invalidate the session
    public void invalidateSession() {
        httpSession.invalidate();
    }

    // Get the current session ID
    public String getSessionId() {
        return httpSession.getId();
    }

    // Check if the session is new
    public boolean isNewSession() {
        return httpSession.isNew();
    }

    // Get session creation time
    public long getCreationTime() {
        return httpSession.getCreationTime();
    }

    // Get last accessed time
    public long getLastAccessedTime() {
        return httpSession.getLastAccessedTime();
    }
}
