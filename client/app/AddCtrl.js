"use strict";

class AddCtrl {
    constructor(workoutsService, $window) {
        this.workoutsService = workoutsService;
        this.$window = $window;
        this.workouts = [];
        this.newWorkoutDate = new Date();
    }

    add() {
        let date = this.newWorkoutDate.toISOString();
        let workoutDate = date.substr(0, date.indexOf("T"));
        let newWorkout = {
            type: this.newWorkoutType,
            duration: "PT" + this.newWorkoutDuration + "M",
            date: workoutDate
        };
        this.workoutsService.addWorkout(newWorkout).then(w => {
            console.log("Added workout", newWorkout);
            this.$window.location.href = "/";
        });
    }
}

AddCtrl.$inject = ["WorkoutsService", "$window"];

export {AddCtrl }
