"use strict";

import moment from "moment";

class AddCtrl {
    constructor(workoutsService, $window) {
        this.workoutsService = workoutsService;
        this.$window = $window;
        this.workouts = [];
        this.types = [];
        this.newWorkoutDate = new Date();

        workoutsService.getAllTypes().then(data => this.types = data.sort());
    }

    add() {
        let newWorkout = {
            type: this.newWorkoutType,
            duration: "PT" + this.newWorkoutDuration + "M",
            date: moment(this.newWorkoutDate).format("YYYY-MM-DD"),
            comment: this.newWorkoutComment
        };
        this.workoutsService.addWorkout(newWorkout).then(w => {
            this.$window.location.href = "/";
        });
    }
}

AddCtrl.$inject = ["WorkoutsService", "$window"];

export {AddCtrl}