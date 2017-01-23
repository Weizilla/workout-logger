"use strict";

import moment from "moment";
import getHost from "./host.js";

class WorkoutsService {
    constructor($http) {
        this.$http = $http;
        this.updateListeners = [];
        this.displayWorkouts = {workouts: []};
    }

    getWorkouts() {
        return this.$http.get(getHost() + "/api/workouts").then(r => r.data);
    }

    refreshGarminEntries() {
        return this.$http.get(getHost() + "/api/garmin/refresh").then(r => r.data);
    }

    getGarminEntries() {
        return this.$http.get(getHost() + "/api/garmin/entry").then(r => r.data);
    }

    getWorkoutsByDate(date) {
        var d = moment(date).format("YYYY-MM-DD");
        return this.$http.get(getHost() + "/api/workouts/dates/" + d)
            .then(r => {
                this.displayWorkouts.date = d;
                this.displayWorkouts.workouts = r.data;
            });
    }

    addWorkout(workout) {
        return this.$http.post(getHost() + "/api/workouts", workout)
            .then(r => r.data)
            .then(() => {
                this.refreshUpdateListeners();
            });
    }

    addEntry(entry) {
        return this.$http.post(getHost() + "/api/entry", entry)
            .then(r => r.data)
            .then(() => {
                this.refreshUpdateListeners();
            });
    }

    deleteWorkout(id) {
        return this.$http.delete(getHost() + "/api/workouts/" + id);
    }

    getWorkoutDates() {
        return this.$http.get(getHost() + "/api/workouts/dates").then(r => r.data.map(d => moment(d)));
    }

    getAllTypes() {
        return this.$http.get(getHost() + "/api/workouts/types").then(r => r.data);
    }

    addUpdateListener(listener) {
        this.updateListeners.push(listener);
    }

    refreshUpdateListeners() {
        this.updateListeners.forEach(l => l.update());
    }

    getGitConfiguration() {
        return this.$http.get(getHost() + "/api/git").then(r => r.data);
    }

    static factory($http) {
        return new WorkoutsService($http);
    }
}

WorkoutsService.factory.$inject = ["$http"];

export { WorkoutsService }
