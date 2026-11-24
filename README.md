# Simple Login App

A minimal Spring Boot web app with a login page backed by an **H2 in-memory database**. Login credentials are validated against users loaded from `data.sql`. Built as a Maven project so it opens directly in IntelliJ IDEA.

## Tech stack
- Java 17
- Spring Boot 3.2.5 (Web + Thymeleaf)
- Maven build system
- H2 in-memory database
- JUnit 5 for tests

## How to open in IntelliJ IDEA

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

## Try it

1. Start the app and open: **http://localhost:8080/login**
2. Log in with one of the demo accounts from `data.sql`, for example:
   - `admin` / `admin123`
3. On successful login you’ll be taken to the welcome page and your session will be created.

### Protected welcome page

- **/welcome** is protected by the login session.
- If you try to access **http://localhost:8080/welcome** directly without logging in, you’ll be redirected to **/login**.

### Logout

Use **/logout** to end the session. After logout, the welcome page will no longer be accessible until you log in again.

## Running the tests

### Unit / controller tests (JUnit)

`LoginControllerTest` checks:
- the login form loads,
- valid demo credentials (from `data.sql`) allow access to the welcome page,
- invalid credentials are rejected,
- `/welcome` redirects to `/login` when no session is present,
- logout ends the session.

In IntelliJ: right-click the `src/test/java` folder → **Run 'All Tests'**.
Or from a terminal in the project folder:

```bash
mvn test
```

### E2E tests (Playwright + Cucumber)

End-to-end tests run in a real browser via Playwright and execute Cucumber scenarios against the running app.

#### Prerequisites

- Java 17 (to run the Spring Boot app)
- Node.js (LTS recommended) + npm (to run the e2e test runner)

#### 1) Start the app

From the project root:

```bash
mvn spring-boot:run
```

Wait until the console shows the app has started, then confirm the app is reachable in a browser.

#### 2) Install npm dependencies

From the project root:

```bash
npm install
```

#### 3) Install Playwright browsers

```bash
npm run e2e:install
```

#### 4) Run the E2E tests

Set the base URL the tests should target (example shown for local dev):

```bash
export E2E_BASE_URL="http://localhost:8080"
npm run e2e
```

On Windows PowerShell:

```powershell
$env:E2E_BASE_URL="http://localhost:8080"
npm run e2e
```

#### Environment variables

- `E2E_BASE_URL` (required): Base URL of the running app, e.g. `http://localhost:8080`.
- `E2E_VALID_USERNAME` (optional): Username used for a "valid login" scenario.
- `E2E_VALID_PASSWORD` (optional): Password used for a "valid login" scenario.

Do **not** put real credentials in docs or source control. Use placeholders and ensure they match the demo users configured for the app (for example, those seeded via `data.sql`).

## Project structure

```
simple-login-app/
├── pom.xml
├── src/main/java/com/example/loginapp/
│   ├── LoginApplication.java      # Spring Boot entry point
│   └── controller/LoginController.java  # GET/POST /login routes, no validation
├── src/main/resources/
│   ├── templates/login.html       # login form
│   ├── templates/welcome.html     # shown after submitting the form
│   ├── static/css/style.css
│   └── application.properties
└── src/test/java/com/example/loginapp/
    └── LoginControllerTest.java   # confirms the form loads + any login succeeds
```

## H2 console (optional)

If enabled in `application.properties`, the H2 web console is available at:
**http://localhost:8080/h2-console**

## Notes / next steps

- Authentication is backed by an H2 in-memory database seeded via `data.sql`.
- For production use, consider storing password hashes (and using Spring Security) instead of plain-text demo passwords.
