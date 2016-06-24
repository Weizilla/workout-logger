import angular from "angular";
import * as CalendarModule from "./calendar/calendar";
import * as WorkoutsModule from "./workouts/workouts";
import * as AddModule from "./add/add";
import { WorkoutsService } from "./WorkoutsService";
import "../app.css";
import "font-awesome-webpack";
import "../index.html";
import "../addWorkout.html";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsService.factory)
    .controller("CalendarCtrl", CalendarModule.ctrl)
    .directive("calendar", () => new CalendarModule.directive())
    .controller("AddCtrl", AddModule.ctrl)
    .directive("add", () => new AddModule.directive())
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl)
    .directive("workouts", () => new WorkoutsModule.directive());


