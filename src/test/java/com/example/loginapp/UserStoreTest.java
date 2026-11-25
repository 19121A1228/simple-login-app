package com.example.loginapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStoreTest {

    private final UserStore userStore = new UserStore();

    @Test
    void adminCredentialsAreAccepted() {
        assertTrue(userStore.isValid("admin", "admin"));
    }

    @Test
    void userCredentialsAreAccepted() {
        assertTrue(userStore.isValid("user", "password"));
    }

    @Test
    void wrongPasswordIsRejected() {
        assertFalse(userStore.isValid("admin", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(userStore.isValid("nobody", "whatever"));
    }

    @Test
    void nullUsernameIsRejected() {
        assertFalse(userStore.isValid(null, "admin"));
    }

    @Test
    void nullPasswordIsRejected() {
        assertFalse(userStore.isValid("admin", null));
    }

    @Test
    void blankUsernameIsRejected() {
        assertFalse(userStore.isValid("   ", "admin"));
    }

    @Test
    void blankPasswordIsRejected() {
        assertFalse(userStore.isValid("admin", "   "));
    }
}
