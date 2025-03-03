Feature: Service now api incident feature

  Background: 
    Given Set the base uri of the service now api
    And Set the base path of the service now api
    And Set the basic Auth of the service now api

  Scenario: Create a incident record and validate the status code
    When Set the header "Content-Type" key and header "application/json" value
    And add the create incident request body as string
    And hit the post http method
    Then validate the status code and status line

  Scenario Outline: Create a incident record body as POJO Object and validate the status code
    When Set the header "Content-Type" key and header "application/json" value
    And create the incident record with description <description> in the request body
    And create the incident record with short description <short_description> in the request body
    And hit the post http method with request body as the pojo object
    Then validate the status code and status line

    Examples: 
      | description   | short_description                            |
      | APISessionJAN | Adding new record using POST POJO Object     |
      | APISessionFEB | Adding new record using POST POJO Object     |
      | APISessionMar | Adding new record using POST POJO Object Mar |
