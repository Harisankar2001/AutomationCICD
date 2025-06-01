@tag
Feature: Error Validation

  @ErrorValidation
  Scenario Outline: Title of your scenario outline
    Given I landed on Ecommerce Page
    When Logged in with username <username> and password <password>
    But "Incorrect email or password." message is displayed
    Examples:
      | username          | password  |
      | hari121@gmail.com | Hari@2001 |