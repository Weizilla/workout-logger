*** Settings ***
Documentation  Exports all entries
Library  Collections
Resource  common.robot


*** Test Cases ***
Refresh Pulls In Garmin Entries
  Drop Int Test Database
  ${manual-entry}=  Read Json File  manual-entry-match.json
  Add Manual Entry  ${manual-entry}
  Refresh Garmin Entries
  ${export}=  Get Export
  ${garmin-entries}=  Get From Dictionary  ${export}  garminEntries
  ${num-garmin-entries}=  Get Length  ${garmin-entries}
  Should Be Equal as Integers  ${num-garmin-entries}  5
  ${manual-entries}=  Get From Dictionary  ${export}  manualEntries
  ${num-manual-entries}=  Get Length  ${manual-entries}
  Should Be Equal as Integers  ${num-manual-entries}  1
  ${workouts}=  Get From Dictionary  ${export}  workouts
  ${num-workouts}=  Get Length  ${workouts}
  Should Be Equal as Integers  ${num-workouts}  1


*** Keywords ***
Get Export
  Create Session  workout-logger  http://${WORKOUT_LOGGER_HOST}
  ${resp}=  Get Request  workout-logger  /api/export
  Should Be Equal As Strings  ${resp.status_code}  200
  [Return]  ${resp.json()}