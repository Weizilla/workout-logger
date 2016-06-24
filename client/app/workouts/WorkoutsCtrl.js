"use strict";

import moment from "moment";

class WorkoutsCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.displayWorkouts = workoutsService.displayWorkouts;
    }
}

WorkoutsCtrl.$inject = ["WorkoutsService"];

export {WorkoutsCtrl};