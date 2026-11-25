Feature: Authentication

  Scenario: Successful login reaches welcome
    Given the application is running
    When I open the login page
    And I log in with username "admin" and password "admin123"
    Then I should see the welcome page with username "admin"

  Scenario: Invalid login shows an error
    Given the application is running
    When I open the login page
    And I log in with username "admin" and password "wrong"
    Then I should see an invalid credentials error

  Scenario: Welcome page requires authentication
    Given the application is running
    When I open the welcome page directly
    Then I should be redirected to the login page

  Scenario: Logout invalidates the session
    Given the application is running
    When I open the login page
    And I log in with username "admin" and password "admin123"
    And I log out
    Then I should be redirected to the login page
    When I open the welcome page directly
    Then I should be redirected to the login page
