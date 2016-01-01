"use strict";

class WorkoutsCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.workouts = [];
        this.init();
    }

    init() {
        this.workoutsService.getWorkouts().then(workouts => {
            this.workouts = workouts;
        });
        this.workoutsService.getWorkoutDates().then(dates => {
            this.workoutDates = dates;
        });
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
            this.init()
        });
    }
}

WorkoutsCtrl.$inject = ["WorkoutsService"];

export { WorkoutsCtrl }
