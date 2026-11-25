package com.example.loginapp.e2e.steps;

import com.example.loginapp.LoginApplication;
import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(classes = LoginApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthSteps {

    @LocalServerPort
    int port;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @After
    public void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        page.navigate(baseUrl() + "/login");
        Assertions.assertTrue(page.url().endsWith("/login"));
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        page.locator("#username").fill(username);
        page.locator("#password").fill(password);
        page.locator("button[type=submit]").click();
    }

    @Then("I should see the welcome page for {string}")
    public void iShouldSeeTheWelcomePageFor(String username) {
        page.waitForURL("**/welcome");
        String content = page.textContent("body");
        Assertions.assertTrue(content.contains("Welcome"));
        Assertions.assertTrue(content.contains(username));
    }

    @Then("I should see an invalid credentials message")
    public void iShouldSeeAnInvalidCredentialsMessage() {
        // stays on /login
        Assertions.assertTrue(page.url().endsWith("/login"));
        Assertions.assertTrue(page.textContent("body").contains("Invalid username or password"));
    }

    @When("I navigate to {string} without logging in")
    public void iNavigateToWithoutLoggingIn(String path) {
        // new context to ensure no session
        context.close();
        context = browser.newContext();
        page = context.newPage();
        page.navigate(baseUrl() + path);
    }

    @Then("I should be on the login page")
    public void iShouldBeOnTheLoginPage() {
        page.waitForURL("**/login");
        Assertions.assertTrue(page.url().endsWith("/login"));
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAsWithPassword(String username, String password) {
        iAmOnTheLoginPage();
        iLogInWithUsernameAndPassword(username, password);
        iShouldSeeTheWelcomePageFor(username);
    }

    @When("I log out")
    public void iLogOut() {
        page.locator("button[type=submit]").click();
    }
}
