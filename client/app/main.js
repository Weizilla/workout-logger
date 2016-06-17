import angular from "angular";
import * as CalendarModule from "./calendar/calendar";
import * as WorkoutsModule from "./workouts";
import * as AddCtrl from "./AddCtrl";
import "purecss";
import "../app.css";
import "font-awesome-webpack";
import "../index.html";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("CalendarCtrl", CalendarModule.ctrl)
    .directive("calendar", () => new CalendarModule.directive())
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl)
    .controller("AddCtrl", AddCtrl.AddCtrl);


