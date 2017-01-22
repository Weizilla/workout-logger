"use strict";

class WorkoutsCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.displayWorkouts = workoutsService.displayWorkouts;
    }

    deleteWorkout(id) {
        this.workoutsService.deleteWorkout(id);
    }
}

WorkoutsCtrl.$inject = ["WorkoutsService"];

export {WorkoutsCtrl};