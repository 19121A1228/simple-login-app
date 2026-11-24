Feature: Authentication and session

  Background:
    Given the user is on the login page

  Scenario: Valid login redirects to welcome
    When the user logs in with valid credentials
    Then the user should see the welcome page

  Scenario: Invalid login shows error on login page
    When the user logs in with invalid credentials
    Then the user should remain on the login page
    And the user should see an invalid credentials error

  Scenario: Welcome requires an authenticated session
    Given the user is not authenticated
    When the user navigates to the welcome page
    Then the user should be redirected to the login page

  Scenario: Logout ends the session
    Given the user is logged in
    When the user logs out
    Then the user should be redirected to the login page
    When the user navigates to the welcome page
    Then the user should be redirected to the login page
