import angular from "angular";
import * as CalendarModule from "./calendar/calendar";
import * as WorkoutsModule from "./workouts";
import * as AddModule from "./add/add";
import "purecss";
import "../app.css";
import "font-awesome-webpack";
import "../index.html";
import "../addWorkout.html";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsModule.service)
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl)
    .controller("CalendarCtrl", CalendarModule.ctrl)
    .directive("calendar", () => new CalendarModule.directive())
    .directive("add", () => new AddModule.directive())
    .controller("AddCtrl", AddModule.ctrl);


