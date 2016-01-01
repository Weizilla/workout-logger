"use strict";

class WorkoutsService {
    constructor($http) {
        this.$http = $http;
        this.host = "http://localhost:80";
    }

    getWorkouts() {
        return this.$http.get(this.host + "/api/workouts").then(r => r.data);
    }

    addWorkout(workout) {
        return this.$http.post(this.host + "/api/workouts", workout).then(r => r.data);
    }

    getWorkoutDates() {
        return this.$http.get(this.host + "/api/workouts/dates").then(r => r.data);
    }

    static factory($http) {
        return new WorkoutsService($http);
    }
};

WorkoutsService.factory.$inject = ["$http"];

export { WorkoutsService }