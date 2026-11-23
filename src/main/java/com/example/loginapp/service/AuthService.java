package com.example.loginapp.service;

import com.example.loginapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValid(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        String normalizedUser = username.trim();
        if (normalizedUser.isEmpty()) {
            return false;
        }

        return userRepository.findPasswordByUsername(normalizedUser)
                .map(storedPassword -> storedPassword.equals(password))
                .orElse(false);
    }
}
