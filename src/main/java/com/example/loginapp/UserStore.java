package com.example.loginapp;

import org.springframework.stereotype.Component;

/**
 * @deprecated This teaching-app "in-memory user store" is obsolete now that authentication is backed by H2.
 * Use {@link AuthService} directly instead.
 *
 * Kept only to avoid confusion when reading older materials/commits. This class is a thin wrapper around
 * {@link AuthService} and does not contain any hard-coded users.
 */
@Deprecated
@Component
public class UserStore {

    private final AuthService authService;

    public UserStore(AuthService authService) {
        this.authService = authService;
    }

    public boolean isValid(String username, String password) {
        return authService.isValid(username, password);
    }
}
