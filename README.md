# Simple Login App

A minimal Spring Boot web app demonstrating **session-based login** with credentials stored in an **in-memory H2 database**.

## Tech stack
- Java 17
- Spring Boot 3.2.5 (Web + Thymeleaf)
- Maven
- H2 (in-memory)
- JUnit 5

## How to run

### In IntelliJ IDEA
1. **File → Open...** → select the `simple-login-app` folder (contains `pom.xml`).
2. Let IntelliJ import the Maven project and download dependencies.
3. Run `src/main/java/com/example/loginapp/LoginApplication.java`.
4. Open: **http://localhost:8080/login**

### From the command line
From the project root:

```bash
mvn spring-boot:run
```

Then open: **http://localhost:8080/login**

## Demo users (seeded via `data.sql`)

Use one of these credentials:

- `admin` / `admin123`
- `user` / `password`
- `alice` / `alice123`

## Auth behavior (demo)

- `GET /login` shows the login form.
- `POST /login` validates the submitted username/password against H2.
- `GET /welcome` is **protected** and requires a valid login session (otherwise redirects to `/login`).
- Logout is performed via **POST `/logout`** (clears the session).

## H2 database / console

- The app uses an **in-memory** H2 database and seeds demo users on startup.
- If enabled in `application.properties`, the H2 console is available at:
  - **http://localhost:8080/h2-console**

## Running tests

```bash
mvn test
```

## E2E tests (Playwright)

Prerequisite: Node.js / npm.

1. Start the app:

```bash
mvn spring-boot:run
```

2. Install E2E dependencies:

```bash
npm install
```

3. Install Playwright browsers:

```bash
npm run pw:install
```

4. Run E2E suite:

```bash
npm run test:e2e
```

## Project structure

```
simple-login-app/
├── pom.xml
├── src/main/java/com/example/loginapp/
│   ├── LoginApplication.java
│   └── controller/LoginController.java
├── src/main/resources/
│   ├── templates/login.html
│   ├── templates/welcome.html
│   ├── static/css/style.css
│   └── application.properties
└── src/test/java/com/example/loginapp/
    └── LoginControllerTest.java
```
