"use strict";
import "./workouts.html"

class WorkoutsDirective {
    constructor() {
        this.templateUrl = "workouts.html";
        this.restrict = "E";
        this.scope = {};
    }
}

export { WorkoutsDirective }