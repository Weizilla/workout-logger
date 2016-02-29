import angular from "angular";
import * as CalendarModule from "./calendar/calendar";
import * as WorkoutsModule from "./workouts";
import "purecss";
import "../app.css";
import "font-awesome-webpack";
import "../index.html";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("CalendarCtrl", CalendarModule.ctrl)
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl);


