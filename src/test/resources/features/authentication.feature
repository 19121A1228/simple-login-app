Feature: Authentication and session-based access
  As a user
  I want to log in and log out
  So that only authenticated users can access the welcome page

  Background:
    Given the application is running

  Scenario: Valid login redirects to welcome and shows username
    Given I open the login page
    When I log in with username "admin" and password "admin123"
    Then I should see the welcome page
    And I should see "admin" displayed

  Scenario: Invalid login stays on login page with an error
    Given I open the login page
    When I log in with username "admin" and password "wrongpassword"
    Then I should stay on the login page
    And I should see an invalid credentials message

  Scenario: Welcome page requires an authenticated session
    When I open the welcome page directly
    Then I should be redirected to the login page

  Scenario: Logout invalidates the session
    Given I open the login page
    When I log in with username "admin" and password "admin123"
    And I log out
    Then I should be redirected to the login page
    When I open the welcome page directly
    Then I should be redirected to the login page
