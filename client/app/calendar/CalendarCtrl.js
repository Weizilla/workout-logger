"use strict";

import moment from "moment";

class CalendarCtrl {
    constructor() {
        this.buildWeeks();
    };

    buildWeeks() {
        this.weeks = [];
        var start = moment().subtract(moment().day(), "d");
        for (let i = 0; i < 4; i++) {
            let weekStart = start.clone().subtract(i, "w");
            this.weeks[4 - i] = this.buildDays(weekStart);
        }
    }

    buildDays(start) {
        let days = [];
        for (let i = 0; i < 7; i++) {
            days[i] = start.clone().add(i, "d").date();
        }
        return days;
    }
}

export { CalendarCtrl };