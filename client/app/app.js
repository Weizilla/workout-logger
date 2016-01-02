import angular from "angular";
import * as WorkoutsModule from "./workouts";
import * as CalendarModule from "./calendar/calendar";
import "bootstrap/css/bootstrap.min.css!";
import "font-awesome/css/font-awesome.min.css!";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl)
    .controller("CalendarCtrl", CalendarModule.ctrl);


