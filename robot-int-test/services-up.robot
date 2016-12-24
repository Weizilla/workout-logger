*** Settings ***
Documentation  Server and dependent services are up
Library  RequestsLibrary

*** Variables ***
${SERVER}   localhost

*** Test Cases ***
Workout Logger server is accessible
  Create Session  workout-logger  http://${SERVER}
  ${resp}=  Get Request  workout-logger  /
  Should Be Equal As Strings  ${resp.status_code}  200

Garmin Connect service is accessible
  Create Session  garmin-connect  https://connect.garmin.com  verify=True
  ${resp}=  Get Request  garmin-connect  /
  Should Be Equal As Strings  ${resp.status_code}  200
