"use strict";
import "./calendar.html";

class CalendarDirective {
    constructor() {
        this.templateUrl = "calendar.html";
        this.restrict = "E";
        this.scope = {};

    }
}

export { CalendarDirective };