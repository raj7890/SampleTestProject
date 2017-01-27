Feature: User wishes to access URL and logIn to application

Background:
Given Launch URL "https://accounts.google.com/ServiceLogin#identifier"
When the user access the URL
Then the user should be presented on webpage


Scenario: Verify Logo
When the user access the URL
Then the user should see Google logo

Scenario: Login functionality
When the user access the URL
And the user enter invalid username and password
Then the user should be logged into application