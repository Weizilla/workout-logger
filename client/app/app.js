import angular from "angular";
import * as CalendarModule from "./calendar/calendar";
import * as WorkoutsModule from "./workouts";
import "bootstrap/css/bootstrap.min.css!";
import "font-awesome/css/font-awesome.min.css!";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("CalendarCtrl", CalendarModule.ctrl)
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl);


