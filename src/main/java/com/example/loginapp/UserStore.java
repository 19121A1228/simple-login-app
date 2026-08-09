package com.example.loginapp;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Very small in-memory "user database" so the demo has both
 * a valid login path and an invalid login path to exercise.
 * Not for production use - passwords are plain text on purpose,
 * for a simple teaching/demo app only.
 */
@Component
public class UserStore {

    private final Map<String, String> users = Map.of(
            "admin", "admin123",
            "john", "password1",
            "mary", "letmein42"
    );

    public boolean isValid(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        String storedPassword = users.get(username.trim());
        return storedPassword != null && storedPassword.equals(password);
    }
}
