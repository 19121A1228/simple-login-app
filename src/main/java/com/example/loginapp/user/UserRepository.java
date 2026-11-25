package com.example.loginapp.user;

import java.util.Optional;

public interface UserRepository {
    Optional<AppUser> findByUsername(String username);
}
