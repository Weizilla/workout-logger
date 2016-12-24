*** Settings ***
Documentation  Add a Manual Entry
Library  OperatingSystem
Library  RequestsLibrary
Library  Collections
Library  MongoDbLibrary.py

*** Variables ***
${WORKOUT_LOGGER_HOST}   localhost
${MONGODB_HOST}  localhost
${MONGODB_DB}  int-test

*** Test Cases ***
Add a Manual Entry
  Drop Int Test Database
  ${workouts}=  Get Workouts By Date  2016-12-22
  Should Be Empty  ${workouts}
  ${input}=  Read Json File  add-manual-entry.json
  Add Manual Entry  ${input}
  ${workouts}=  Get Workouts By Date  2016-12-22
  ${num-workouts}=  Get Length  ${workouts}
  Should Be Equal as Integers  ${num-workouts}  1
  ${actual-workout}=  Get From List  ${workouts}  0
  Dictionary Should Contain Item  ${actual-workout}  type  add manual entry test
  Dictionary Should Contain Item  ${actual-workout}  duration  PT1H10M
  Dictionary Should Contain Item  ${actual-workout}  date  2016-12-22
  Dictionary Should Contain Item  ${actual-workout}  comment  ADD MANUAL ENTRY INT TEST

*** Keywords ***
Drop Int Test Database
  Connect To Mongodb  ${MONGODB_HOST}
  Drop Mongodb Database  ${MONGODB_DB}

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
