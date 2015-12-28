"use strict";

class WorkoutsService {
    constructor($http) {
        this.$http = $http;
    }

    getWorkouts() {
        return this.$http.get("http://localhost:8080/api/workouts").then(r => r.data);
    }

    static factory($http) {
        return new WorkoutsService($http);
    }
};

WorkoutsService.factory.$inject = ["$http"];

export { WorkoutsService }