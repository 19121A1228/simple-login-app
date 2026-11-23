package com.example.loginapp.repository;

import java.util.Optional;

public interface UserRepository {

    Optional<String> findPasswordByUsername(String username);
}
