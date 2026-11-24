MERGE INTO users (username, password) KEY (username) VALUES ('admin', 'admin123');
MERGE INTO users (username, password) KEY (username) VALUES ('user', 'password');
MERGE INTO users (username, password) KEY (username) VALUES ('alice', 'alice123');
