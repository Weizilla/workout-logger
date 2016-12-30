*** Settings ***
Documentation  Add a Manual Entry
Library  Collections
Resource  common.robot


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
  Dictionary Should Contain Item  ${actual-workout}  rating  5
  Dictionary Should Contain Item  ${actual-workout}  comment  ADD MANUAL ENTRY INT TEST
