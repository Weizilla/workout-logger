"use strict";
import "./garmin.html";

class GarminDirective {
    constructor() {
        this.templateUrl = "garmin.html";
        this.restrict = "E";
        this.scope = {};
    }
}

export { GarminDirective };