"use strict";

import moment from "moment";
import getHost from "./host.js";

class WorkoutsService {
    constructor($http) {
        this.$http = $http;
        this.updateListeners = [];
    }

    getWorkouts() {
        return this.$http.get(getHost() + "/api/workouts").then(r => r.data);
    }

    getWorkoutsByDate(date) {
        var d = moment(date).format("YYYY-MM-DD");
        return this.$http.get(getHost() + "/api/workouts/dates/" + d).then(r => r.data);
    }

    addWorkout(workout) {
        return this.$http.post(getHost() + "/api/workouts", workout)
            .then(r => r.data)
            .then(() => {
                this.refreshUpdateListeners();
            });
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

    static factory($http) {
        return new WorkoutsService($http);
    }
}

WorkoutsService.factory.$inject = ["$http"];

export { WorkoutsService }
