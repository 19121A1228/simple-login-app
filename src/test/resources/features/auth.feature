Feature: Authentication
  Users must be able to log in with valid credentials, be blocked from /welcome when unauthenticated,
  and be able to log out.

  Scenario: Successful login navigates to welcome
    Given I am on the login page
    When I log in with username "demo1" and password "password1"
    Then I should see the welcome page for "demo1"

  Scenario: Invalid login stays on login page with error
    Given I am on the login page
    When I log in with username "demo1" and password "wrong"
    Then I should see an invalid credentials message

  Scenario: Unauthenticated access to welcome redirects to login
    When I navigate to "/welcome" without logging in
    Then I should be on the login page

  Scenario: Logout clears session
    Given I am logged in as "demo1" with password "password1"
    When I log out
    Then I should be on the login page
    When I navigate to "/welcome" without logging in
    Then I should be on the login page
