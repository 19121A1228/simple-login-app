# Simple Login App

A minimal Spring Boot web app demonstrating **session-based login** with credential validation against an **in-memory H2 database**.

## Tech stack
- Java 17
- Spring Boot 3.2.5 (Web + Thymeleaf)
- Maven
- H2 (in-memory)
- JUnit 5
- Playwright (E2E, via Maven profile)

## Auth behavior (demo)

- `GET /login` shows the login form.
- `POST /login` validates the submitted username/password against H2.
- `GET /welcome` is **session-protected** and requires a valid login session (otherwise redirects to `/login`).
- Logout is performed via **POST `/logout`** (clears the session).

## Demo users (seeded via `data.sql`) — demo only

These credentials are for demonstration/testing only:

- `demo1` / `password1`
- `demo2` / `password2`

## How to run locally

### In IntelliJ IDEA
1. **File → Open...** → select the `simple-login-app` folder (contains `pom.xml`).
2. Let IntelliJ import the Maven project and download dependencies.
3. Run `src/main/java/com/example/loginapp/LoginApplication.java`.
4. Open: **http://localhost:8080/login**

### From the command line (dev)
From the project root:

```bash
mvn spring-boot:run
```

Then open: **http://localhost:8080/login**

### Build a runnable JAR and run it
From the project root:

```bash
mvn package
java -jar target/*.jar
```

Then open: **http://localhost:8080/login**

## H2 database / console

- The app uses an **in-memory** H2 database and seeds demo users on startup.
- H2 Console:
  - **http://localhost:8080/h2-console**
- JDBC URL (in-memory):
  - `jdbc:h2:mem:testdb`

> Note: If you change the datasource name in `application.properties`, the JDBC URL must match it.

## Running tests

### Unit tests
```bash
mvn test
```

### E2E tests (Playwright)
Run the E2E suite using the Maven profile:

```bash
mvn verify -P e2e
```

Playwright downloads the required browsers automatically on the first run (this may take a few minutes).

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
