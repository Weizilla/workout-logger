import angular from "angular";
import * as CalendarModule from "./calendar/calendar";
import * as WorkoutsModule from "./workouts/workouts";
import * as AddModule from "./add/add";
import * as GarminModule from "./garmin/garmin";
import { WorkoutsService } from "./WorkoutsService";
import "bootswatch/cosmo/bootstrap.min.css";
import "font-awesome-webpack";
import "../index.html";
import "../addWorkout.html";
import "../garminEntries.html";
import "../app.css";

angular.module("workoutLoggerApp", [])
    .factory("WorkoutsService", WorkoutsService.factory)
    .controller("CalendarCtrl", CalendarModule.ctrl)
    .directive("calendar", () => new CalendarModule.directive())
    .controller("AddCtrl", AddModule.ctrl)
    .directive("add", () => new AddModule.directive())
    .controller("WorkoutsCtrl", WorkoutsModule.ctrl)
    .directive("workouts", () => new WorkoutsModule.directive())
    .controller("GarminCtrl", GarminModule.ctrl)
    .directive("garmin", () => new GarminModule.directive());


