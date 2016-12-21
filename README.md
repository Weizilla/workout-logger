# Workout Logger
Logs workouts

[![Build Status](https://travis-ci.org/Weizilla/workout-logger.svg?branch=master)](https://travis-ci.org/Weizilla/workout-logger)

[![Coverage Status](https://coveralls.io/repos/Weizilla/workout-logger/badge.svg?branch=master&service=github)](https://coveralls.io/github/Weizilla/workout-logger?branch=master)

## Instructions
1. Copy conf/sample.yaml to conf/application.yaml and populate with the correct values

## Workflows
### Manual entry
1. Add workout -> manual entry is created
2. Run match process for new entry's date

### Garmin sync entry
1. Garmin sync downloads new workout -> garmin entry is created
2. Run match process for each date of new garmin entry

### Match process
1. For all manual entries without corresponding workouts:
2. Look for garmin entry of same date and type
3. If match, create workout with two entries as linked ids, using garmin for details
4. If not match, create workout with only manual entry as linked id

## Completed
1. Spring boot, Angular JS, Java 8
2. Store workouts in Mongo db or memory depending on Spring profile
3. Run using Docker with images stored in Docker Hub
4. Deployed to AWS using ECS

## Bugs
1. Fix Docker due to garmin login changes. Add set up to readme. Pass in username and password from env to docker

## To Do
1. Improve UI
 1. Improve workout day, today, selected styles
 2. Parse duration
 3. Unselect selected day
 4. Calendar, Add dialog and workouts on same page for larger screens
 5. Change url for selected day
 6. Use angular routes instead of different pages
2. Import/Export
3. Workouts
 1. Add day ordering of workout (including reordering)
 2. Edit/delete workout
 3. 5 star rating
4. Most common times for each time
5. DB backup scheme
6. Deployment
 1. Document
 2. Store AWS config in Git?
7. Logging
8. Nagios
9. Password/security for UI - Maybe use Google Sign In?
10. Https
11. Garmin Connect Sync
 1. Get rid of package string for component scan
 2. Use futures, async, netty?
 3. UI: distance
 4. Better error handling
 5. Sync custom time range
 6. Scheduled sync
 7. Match garmin types to existing types?
12. Unit tests for UI
 1. add additional unit tests
 2. add browser based tests (Karma)
14. Combine JS and Java code coverage in Coveralls.io
15. Spring hot deploy
16. Match garmin and manual workouts
17. Add state to json and controller tests
18. Add integration test with Docker, Robot Framework
 1. Add robot framework install and run instructions
 2. Add services up test for mongo
 3. Add tests
  1. Add workout
  2. Gets garmin workouts
  3. Matches workouts
