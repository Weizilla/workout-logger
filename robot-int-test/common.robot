*** Settings ***
Documentation  Common Variables and Keywords
Library  OperatingSystem
Library  RequestsLibrary
Library  Collections
Library  python/MongoDbLibrary.py

*** Variables ***
${WORKOUT_LOGGER_HOST}   localhost
${MONGODB_HOST}  localhost
${MONGODB_DB}  int-test

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
