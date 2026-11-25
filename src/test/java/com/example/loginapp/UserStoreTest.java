package com.example.loginapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStoreTest {

    private final UserStore userStore = new UserStore();

    @Test
    void adminCredentialsAreAccepted() {
        assertTrue(userStore.authenticate("admin", "admin"));
    }

    @Test
    void userCredentialsAreAccepted() {
        assertTrue(userStore.authenticate("user", "password"));
    }

    @Test
    void wrongPasswordIsRejected() {
        assertFalse(userStore.authenticate("admin", "wrongpassword"));
    }

    @Test
    void unknownUsernameIsRejected() {
        assertFalse(userStore.authenticate("nobody", "whatever"));
    }
}
