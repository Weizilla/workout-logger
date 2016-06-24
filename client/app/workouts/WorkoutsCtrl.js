"use strict";

import moment from "moment";

class WorkoutsCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.workouts = [];
    }
}

WorkoutsCtrl.$inject = ["WorkoutsService"];

export {WorkoutsCtrl};