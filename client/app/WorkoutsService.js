"use strict";

import moment from "moment";

class WorkoutsService {
    constructor($http) {
        this.$http = $http;
        this.host = "http://localhost:80";
        this.updateListeners = [];
    }

    getWorkouts() {
        return this.$http.get(this.host + "/api/workouts").then(r => r.data);
    }

    addWorkout(workout) {
        return this.$http.post(this.host + "/api/workouts", workout)
            .then(r => {
                this.refreshUpdateListeners();
                return r.data
            });
    }

    getWorkoutDates() {
        return this.$http.get(this.host + "/api/workouts/dates")
            .then(r => r.data.map(d => moment(d)));
    }

    addUpdateListener(listener) {
        this.updateListeners.push(listener);
    }

    refreshUpdateListeners() {
        this.updateListeners.forEach(l => l());
    }

    static factory($http) {
        return new WorkoutsService($http);
    }
};

WorkoutsService.factory.$inject = ["$http"];

export { WorkoutsService }