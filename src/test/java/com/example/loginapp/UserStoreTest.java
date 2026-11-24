package com.example.loginapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void seededAdminCredentialsAreAccepted() {
        assertTrue(userRepository.isValid("admin", "admin123"));
    }

    @Test
    void wrongPasswordIsRejected() {
        assertFalse(userRepository.isValid("admin", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(userRepository.isValid("nobody", "whatever"));
    }
}
