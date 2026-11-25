package com.example.loginapp.e2e.steps;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AuthSteps {

    private static ConfigurableApplicationContext context;
    private static String baseUrl;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    @Given("the application is running")
    public void theApplicationIsRunning() {
        if (context == null) {
            context = SpringApplication.run(com.example.loginapp.LoginApplication.class,
                    "--server.port=0");
            int port = Integer.parseInt(context.getEnvironment().getProperty("local.server.port"));
            baseUrl = "http://localhost:" + port;
        }
        assertNotNull(baseUrl);
    }

    @Before
    public void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.setDefaultTimeout(Duration.ofSeconds(10).toMillis());
    }

    @After
    public void tearDownBrowser() {
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Given("I open the login page")
    public void iOpenTheLoginPage() {
        page.navigate(baseUrl + "/login");
        assertTrue(page.url().endsWith("/login"));
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        page.locator("input[name='username']").fill(username);
        page.locator("input[name='password']").fill(password);
        page.locator("button[type='submit'], input[type='submit']").first().click();
    }

    @When("I open the welcome page directly")
    public void iOpenTheWelcomePageDirectly() {
        page.navigate(baseUrl + "/welcome");
    }

    @When("I log out")
    public void iLogOut() {
        // Supports either a form button or a link; prefers POST via form/button
        Locator logoutButton = page.locator("text=Log out").first();
        if (logoutButton.count() > 0) {
            logoutButton.click();
        } else {
            page.navigate(baseUrl + "/logout");
        }
    }

    @Then("I should see the welcome page")
    public void iShouldSeeTheWelcomePage() {
        // app may redirect, so check for welcome content
        assertTrue(page.content().toLowerCase().contains("welcome"));
    }

    @Then("I should see {string} displayed")
    public void iShouldSeeDisplayed(String username) {
        assertTrue(page.content().contains(username));
    }

    @Then("I should stay on the login page")
    public void iShouldStayOnTheLoginPage() {
        assertTrue(page.url().endsWith("/login"));
    }

    @Then("I should see an invalid credentials message")
    public void iShouldSeeAnInvalidCredentialsMessage() {
        String content = page.content().toLowerCase();
        assertTrue(content.contains("invalid") || content.contains("incorrect"));
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        assertTrue(page.url().endsWith("/login"));
    }
}
