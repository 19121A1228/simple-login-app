package com.example.loginapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void demo1CredentialsAreAccepted() {
        assertTrue(authService.authenticate("demo1", "password1"));
    }

    @Test
    void demo2CredentialsAreAccepted() {
        assertTrue(authService.authenticate("demo2", "password2"));
    }

    @Test
    void wrongPasswordIsRejected() {
        assertFalse(authService.authenticate("demo1", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(authService.authenticate("nobody", "whatever"));
    }
}
