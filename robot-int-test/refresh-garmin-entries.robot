*** Settings ***
Documentation  Refresh Garmin Entries
Library  OperatingSystem
Library  RequestsLibrary
Library  Collections
Library  MongoDbLibrary.py

*** Variables ***
${WORKOUT_LOGGER_HOST}   localhost
${MONGODB_HOST}  localhost
${MONGODB_DB}  int-test

*** Test Cases ***
Refresh Pulls In Garmin Entries
  Drop Int Test Database
  Refresh Garmin Entries
  ${garmin-entries}=  Get Garmin Entries
  ${num-garmin-entries}=  Get Length  ${garmin-entries}
  Should Be Equal as Integers  ${num-garmin-entries}  5

*** Keywords ***
Drop Int Test Database
  Connect To Mongodb  ${MONGODB_HOST}
  Drop Mongodb Database  ${MONGODB_DB}

Refresh Garmin Entries
  Create Session  workout-logger  http://${WORKOUT_LOGGER_HOST}
  ${resp}=  Get Request  workout-logger  /api/garmin/refresh
  Should Be Equal As Strings  ${resp.status_code}  200

Get Garmin Entries
  Create Session  workout-logger  http://${WORKOUT_LOGGER_HOST}
  ${resp}=  Get Request  workout-logger  /api/garmin/entry
  Should Be Equal As Strings  ${resp.status_code}  200
  [Return]  ${resp.json()}
