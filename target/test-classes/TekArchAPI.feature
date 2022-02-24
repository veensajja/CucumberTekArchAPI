
Feature: Test TekArchAPI

 
  Scenario: Login to TekArch
    Given The endpoint
    When I send post login method
    Then validate the status code 
    And get token
   
	Scenario: Create user on TekArch
    Given The token
    And I set the create user data
    When I send post create user method
    Then validate the status code 
    And validate the content type
    
Scenario: Get user from TekArch
		Given The token
    When I get the create user data by get method
    Then validate the status code for get user
    And validate the content type
    And get user data from response