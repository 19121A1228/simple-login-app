-- Seed demo users into app_user with BCrypt password hashes (no raw passwords stored).
-- Demo raw passwords (do not store): password1, password2
INSERT INTO app_user (username, password_hash)
VALUES ('demo1', '$2a$10$wqCw3Y0Yh1zP8r6v2GmKp.4lqv8mK0EoL0lKcQ4b0mG0x1gQq3Y1y');

INSERT INTO app_user (username, password_hash)
VALUES ('demo2', '$2a$10$2Qb3x9bJHfYf5xYfJ2mY9O6zQxVfQH3G9p3m3o3lq8Cq1gJ3G2o8a');
