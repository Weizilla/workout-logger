import angular from "angular";
import * as WorkoutsModule from "./workouts";
import "bootstrap/css/bootstrap.min.css!";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl);


