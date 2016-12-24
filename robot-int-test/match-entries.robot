*** Settings ***
Documentation  Match Manual and Garmin Entries
Library  OperatingSystem
Library  RequestsLibrary
Library  Collections
Library  python/MongoDbLibrary.py

*** Variables ***
${WORKOUT_LOGGER_HOST}   localhost
${MONGODB_HOST}  localhost
${MONGODB_DB}  int-test

*** Test Cases ***
Garmin Refresh Matches Existing Manual Entry
  Drop Int Test Database
  ${manual-entry}=  Read Json File  manual-entry-match.json
  Add Manual Entry  ${manual-entry}
  Refresh Garmin Entries
  ${workouts}=  Get Workouts By Date  2016-12-23
  ${num-workouts}=  Get Length  ${workouts}
  Should Be Equal as Integers  ${num-workouts}  1
  ${matched-workout}=  Get From List  ${workouts}  0
  Dictionary Should Contain Item  ${matched-workout}  type  cycling
  Dictionary Should Contain Item  ${matched-workout}  duration  PT1H10M
  Dictionary Should Contain Item  ${matched-workout}  date  2016-12-23
  Dictionary Should Contain Item  ${matched-workout}  garminId  1493610294
  Dictionary Should Contain Item  ${matched-workout}  comment  GARMIN ENTRY MATCH TEST

Adding Manual Entry Matches Against Existing Garmin Entries
  Drop Int Test Database
  Refresh Garmin Entries
  ${manual-entry}=  Read Json File  manual-entry-match.json
  Add Manual Entry  ${manual-entry}
  ${workouts}=  Get Workouts By Date  2016-12-23
  ${num-workouts}=  Get Length  ${workouts}
  Should Be Equal as Integers  ${num-workouts}  1
  ${matched-workout}=  Get From List  ${workouts}  0
  Dictionary Should Contain Item  ${matched-workout}  type  cycling
  Dictionary Should Contain Item  ${matched-workout}  duration  PT1H10M
  Dictionary Should Contain Item  ${matched-workout}  date  2016-12-23
  Dictionary Should Contain Item  ${matched-workout}  garminId  1493610294
  Dictionary Should Contain Item  ${matched-workout}  comment  GARMIN ENTRY MATCH TEST

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

Get Workouts By Date
  [Arguments]  ${date}
  Create Session  workout-logger  http://${WORKOUT_LOGGER_HOST}
  ${resp}=  Get Request  workout-logger  /api/workouts/dates/${date}
  Should Be Equal As Strings  ${resp.status_code}  200
  [Return]  ${resp.json()}

Add Manual Entry
  [Arguments]  ${input}
  ${headers}=  Create Dictionary  Content-Type=application/json  Accept=application/json
  Create Session  workout-logger  http://${WORKOUT_LOGGER_HOST}
  ${resp}=  Post Request  workout-logger  /api/entry  data=${input}  headers=${headers}
  Should Be Equal As Strings  ${resp.status_code}  200

Read Json File
  [Arguments]  ${filename}
  ${contents}=  Get File  robot-int-test/data/${filename}
  ${json}=  To Json  ${contents}
  [Return]  ${json}