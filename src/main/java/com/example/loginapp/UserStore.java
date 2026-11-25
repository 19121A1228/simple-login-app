package com.example.loginapp;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Legacy/teaching-only component kept for reference.
 *
 * <p><strong>Unused:</strong> Authentication is handled by the H2-backed {@code AuthService}.</p>
 *
 * <p>Kept only to avoid confusion when reading older materials/commits. This class validates credentials against
 * the H2 database and does not contain any hard-coded users.</p>
 *
 * <p>This is a demo app: passwords are intentionally stored and compared as plain text.</p>
 *
 * @deprecated Unused in favor of the H2-backed {@code AuthService}. Kept for teaching/reference only.
 */
@Deprecated(forRemoval = false, since = "legacy")
public class UserStore {

    private final JdbcTemplate jdbcTemplate;

    public UserStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isValid(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String trimmedUsername = username.trim();
        if (trimmedUsername.isBlank()) {
            return false;
        }

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?",
                Integer.class,
                trimmedUsername,
                password
        );

        return count != null && count > 0;
    }
}
