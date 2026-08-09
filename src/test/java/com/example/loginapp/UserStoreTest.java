package com.example.loginapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStoreTest {

    private final UserStore userStore = new UserStore();

    @Test
    void validCredentialsAreAccepted() {
        assertTrue(userStore.isValid("admin", "admin123"));
        assertTrue(userStore.isValid("john", "password1"));
    }

    @Test
    void invalidPasswordIsRejected() {
        assertFalse(userStore.isValid("admin", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(userStore.isValid("nobody", "whatever"));
    }

    @Test
    void nullValuesAreRejected() {
        assertFalse(userStore.isValid(null, "admin123"));
        assertFalse(userStore.isValid("admin", null));
    }
}
