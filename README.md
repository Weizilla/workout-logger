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
1. Improve UI?
2. Import/Export
3. Add day ordering of workout (including reordering)
4. Most common times (for type)
5. DB backup scheme
6. Deployment
 1. Document
 2. Store AWS config in Git
7. Logging
8. Nagios?
9. Password/security for UI - Maybe use Google Sign In?
10. Https
11. Garmin Connect Sync
12. Unit tests for UI
 1. Figure out how to use ES6 with unit tests both in CLI and Intellij
 2. Figure out JS only test along with browser test (Karma?)
13. BUG: Adding workout shows up for the next day. Maybe due to timezone parsing because it happened at end of day?
14. Combine JS and Java code coverage in Coveralls.io
