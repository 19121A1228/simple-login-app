package com.example.loginapp;

import com.example.loginapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserStoreTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void seededAdminCredentialsAreAccepted() {
        assertTrue(userRepository.isValidCredentials("admin", "admin123"));
    }

    @Test
    void wrongPasswordIsRejected() {
        assertFalse(userRepository.isValidCredentials("admin", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(userRepository.isValidCredentials("nobody", "whatever"));
    }
}
