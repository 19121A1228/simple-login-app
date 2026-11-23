package com.example.loginapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStoreTest {

    // Legacy/demo component: authentication is now backed by H2 (source of truth).
    // These tests remain to document and guard the legacy behavior of UserStore.
    private final UserStore userStore = new UserStore();

    @Test
    void legacyValidCredentialsAreAccepted() {
        assertTrue(userStore.isValid("admin", "admin123"));
        assertTrue(userStore.isValid("john", "password1"));
    }

    @Test
    void legacyInvalidPasswordIsRejected() {
        assertFalse(userStore.isValid("admin", "wrongpassword"));
    }

    @Test
    void legacyUnknownUsernameIsRejected() {
        assertFalse(userStore.isValid("nobody", "whatever"));
    }

    @Test
    void legacyNullValuesAreRejected() {
        assertFalse(userStore.isValid(null, "admin123"));
        assertFalse(userStore.isValid("admin", null));
    }
}
