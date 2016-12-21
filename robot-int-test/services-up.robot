*** Settings ***
Documentation  Tests to see that all the services are up
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

Garmin SSO service is accessible
  Create Session  garmin-sso  https://sso.garmin.com  verify=True
  ${resp}=  Get Request  garmin-sso  /sso
  Should Be Equal As Strings  ${resp.status_code}  200
