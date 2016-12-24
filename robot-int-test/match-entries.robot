*** Settings ***
Documentation  Match Manual and Garmin Entries
Library  Collections
Resource  common.robot


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
