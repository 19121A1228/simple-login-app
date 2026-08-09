# Simple Login App

A minimal Spring Boot web app with a login page. There is **no
validation** - any username/password combination submitted is accepted
and takes you straight to the welcome page. Built as a Maven project so
it opens directly in IntelliJ IDEA.

## Tech stack
- Java 17
- Spring Boot 3.2.5 (Web + Thymeleaf)
- Maven build system
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

Type literally anything into the username and password fields and submit
— you'll always land on the welcome page with your username shown.

## Running the tests

`LoginControllerTest` checks the login form loads and that any submitted
credentials reach the welcome page. In IntelliJ: right-click the
`src/test/java` folder → **Run 'All Tests'**. Or from a terminal in the
project folder:

```bash
mvn test
```

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

## Notes / next steps

- There's intentionally no credential checking, no user store, and no
  session/authentication state — submitting the form always succeeds.
- If you later want real authentication, add a user store back in and
  either check credentials manually or bring in Spring Security with
  hashed passwords.
