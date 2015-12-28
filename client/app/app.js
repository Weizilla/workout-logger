import angular from "angular";
import * as WorkoutsModule from "./workouts";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl);


