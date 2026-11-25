package com.example.loginapp.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final RowMapper<AppUser> USER_ROW_MAPPER = (rs, rowNum) -> new AppUser(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password_hash")
    );

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        List<AppUser> users = jdbcTemplate.query(
                "SELECT id, username, password_hash FROM app_user WHERE username = ?",
                USER_ROW_MAPPER,
                username
        );
        return users.stream().findFirst();
    }
}
