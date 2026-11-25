package com.example.loginapp;

import java.util.Map;

/**
 * Legacy/teaching-only component kept for reference.
 *
 * <p><strong>Unused:</strong> Authentication is handled elsewhere in the application.</p>
 *
 * <p>Kept only to avoid confusion when reading older materials/commits. This class uses a hard-coded in-memory
 * map of users and does not connect to any database.</p>
 *
 * <p>This is a demo app: passwords are intentionally stored and compared as plain text.</p>
 *
 * @deprecated Unused. Kept for teaching/reference only.
 */
@Deprecated(forRemoval = false, since = "legacy")
public class UserStore {

    private final Map<String, String> users = Map.of(
            "admin", "admin",
            "user", "password"
    );

    public boolean isValid(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String trimmedUsername = username.trim();
        if (trimmedUsername.isBlank()) {
            return false;
        }

        return password.equals(users.get(trimmedUsername));
    }
}
