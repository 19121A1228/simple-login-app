Feature: Authentication
  As a user
  I want to log in and log out
  So that access to /welcome is protected

  Scenario: Successful login shows welcome page
    Given the app is running
    When I open the login page
    And I log in with username "admin" and password "admin123"
    Then I should see the welcome page
    And I should see "admin" on the page

  Scenario: Invalid login shows error
    Given the app is running
    When I open the login page
    And I log in with username "admin" and password "wrong"
    Then I should see the login page
    And I should see "Invalid username or password." on the page

  Scenario: Welcome page requires authentication
    Given the app is running
    When I open the welcome page
    Then I should be redirected to the login page

  Scenario: Logout clears session
    Given the app is running
    When I open the login page
    And I log in with username "user" and password "password"
    And I log out
    And I open the welcome page
    Then I should be redirected to the login page
