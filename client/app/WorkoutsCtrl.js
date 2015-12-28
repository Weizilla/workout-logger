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
    }
}

WorkoutsCtrl.$inject = ["WorkoutsService"];

export { WorkoutsCtrl }
