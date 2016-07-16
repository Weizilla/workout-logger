# Workout Logger
Logs workouts

[![Build Status](https://travis-ci.org/Weizilla/workout-logger.svg?branch=master)](https://travis-ci.org/Weizilla/workout-logger)

[![Coverage Status](https://coveralls.io/repos/Weizilla/workout-logger/badge.svg?branch=master&service=github)](https://coveralls.io/github/Weizilla/workout-logger?branch=master)

## Instructions
Coming soon

## Completed
1. Spring boot, Angular JS, Java 8
2. Store workouts in Mongo db or memory depending on Spring profile
3. Run using Docker with images stored in Docker Hub
4. Deployed to AWS using ECS 

## To Do
1. Improve UI
 1. Improve workout day, today, selected styles 
 2. Parse duration
 3. Unselect selected day
 4. Calendar, Add dialog and workouts on same page for larger screens
2. Import/Export
3. Workouts
 1. Add day ordering of workout (including reordering)
 2. Edit/delete workout
 3. 5 star rating
4. Most common times for each time
5. DB backup scheme
6. Deployment
 1. Document
 2. Store AWS config in Git
7. Logging
8. Nagios?
9. Password/security for UI - Maybe use Google Sign In?
10. Https
11. Garmin Connect Sync
 1. Find safe place for username/password
 2. Get rid of package string for component scan
 3. Use futures, async, netty?
 4. UI: distance
 5. Better error handling
 6. Sync custom time range
 7. Scheduled sync
 9. Match garmin types to existing types?
12. Unit tests for UI
 1. add additional unit tests
 2. add browser based tests (Karma)
14. Combine JS and Java code coverage in Coveralls.io
15. Spring hot deploy
16. Match garmin and manual workouts
