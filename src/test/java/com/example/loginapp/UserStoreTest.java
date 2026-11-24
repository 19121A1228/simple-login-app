package com.example.loginapp;

import com.example.loginapp.UserStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserStoreTest {

    @Autowired
    private UserStore userStore;

    @Test
    void seededAdminCredentialsAreAccepted() {
        assertTrue(userStore.isValid("admin", "admin123"));
    }

    @Test
    void seededUserCredentialsAreAccepted() {
        assertTrue(userStore.isValid("user", "password"));
    }

    @Test
    void seededAliceCredentialsAreAccepted() {
        assertTrue(userStore.isValid("alice", "alice123"));
    }

    @Test
    void wrongPasswordIsRejected() {
        assertFalse(userStore.isValid("admin", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(userStore.isValid("nobody", "whatever"));
    }
}
