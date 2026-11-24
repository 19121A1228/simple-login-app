# Simple Login App

A minimal Spring Boot web app with a login page backed by an H2 database.
The login form **validates credentials against seeded users** in H2. The
`/welcome` page **requires a valid login session**.

## Tech stack
- Java 17
- Spring Boot 3.2.5 (Web + Thymeleaf)
- Maven build system
- H2 database (in-memory)
- JUnit 5 for tests

## How to run

### In IntelliJ IDEA

1. **File → Open...** and select the `simple-login-app` folder (the one
   containing `pom.xml`).
2. IntelliJ will detect it as a Maven project and prompt to import it —
   click **Load Maven Project** (or it may do this automatically).
   IntelliJ will download the dependencies from Maven Central.
3. Once indexing/import finishes, open
   `src/main/java/com/example/loginapp/LoginApplication.java`.
4. Click the green **Run ▶** arrow next to the `main` method
   (or right-click the file → **Run 'LoginApplication'**).
5. Once the console shows `Started LoginApplication`, open a browser to:
   **http://localhost:8080/login**

### From the command line

From the project root:

```bash
mvn spring-boot:run
```

Then open: **http://localhost:8080/login**

## Seeded users (H2)

The app seeds a small set of users into the H2 database on startup. Use
one of the following credentials to log in:

- `admin` / `admin123`
- `user` / `password`
- `alice` / `alice123`

> Note: The exact seed data lives in the project’s SQL/data seeding files.

## Login / session / logout behavior

- `GET /login` shows the login form.
- `POST /login` validates the submitted username/password against H2.
- `GET /welcome` requires an authenticated session. If you are not logged
  in, you will be redirected to `/login`.
- Logout is performed via **POST `/logout`** (clears the login session).

## H2 console

If enabled in `application.properties`, the H2 console is available at:

- **http://localhost:8080/h2-console**

(Use the JDBC URL/credentials configured in `application.properties`.)

## Running the tests

In IntelliJ: right-click the `src/test/java` folder → **Run 'All Tests'**.

Or from a terminal in the project folder:

```bash
mvn test
```

## E2E tests (Playwright + Gherkin)

The project also includes an end-to-end (E2E) test suite using **Playwright** with **Gherkin**-style feature files.

### Prerequisites

- **Node.js / npm** installed (required to run the E2E tests)

### Run the app (required)

The E2E suite expects the application to already be running at:

- **http://localhost:8080**

Start the app from the project root:

```bash
mvn spring-boot:run
```

### Install E2E dependencies

From the project root:

```bash
npm ci
```

### Install Playwright browsers

```bash
npm run pw:install
```

### Run the E2E suite

```bash
npm run test:e2e
```

## Project structure

```
simple-login-app/
├── pom.xml
├── src/main/java/com/example/loginapp/
│   ├── LoginApplication.java      # Spring Boot entry point
│   └── controller/LoginController.java  # login/session routes
├── src/main/resources/
│   ├── templates/login.html       # login form
│   ├── templates/welcome.html     # shown after login
│   ├── static/css/style.css
│   └── application.properties
└── src/test/java/com/example/loginapp/
    └── LoginControllerTest.java   # confirms login + session requirements
```

## Notes / next steps

- This app uses simple session-based authentication for demonstration.
- If you later want real authentication, consider Spring Security with
  hashed passwords and a proper user store.
