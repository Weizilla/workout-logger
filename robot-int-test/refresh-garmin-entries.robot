*** Settings ***
Documentation  Refresh Garmin Entries
Library  Collections
Resource  common.robot


*** Test Cases ***
Refresh Pulls In Garmin Entries
  Drop Int Test Database
  Refresh Garmin Entries
  ${garmin-entries}=  Get Garmin Entries
  ${num-garmin-entries}=  Get Length  ${garmin-entries}
  Should Be Equal as Integers  ${num-garmin-entries}  5
