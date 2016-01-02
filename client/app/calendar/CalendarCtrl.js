"use strict";

import moment from "moment";

class CalendarCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.weeks = this.buildWeeks();
        this.update();
        this.workoutsService.addUpdateListener(this.update);
    };

    update() {
        //TODO "this" isn't defined
        if (this) {
            this.workoutsService.getWorkoutDates().then(dates => {
                this.weeks = this.buildWeeks(dates)
            });
        }
    }

    buildWeeks(workoutDates) {
        let numWeeks = 4;
        let weeks = [];
        var start = moment().startOf("week");
        for (let i = 0; i < numWeeks; i++) {
            let weekStart = start.clone().subtract(i, "w");
            weeks[numWeeks - i] = this.buildDays(weekStart, workoutDates);
        }
        return weeks;
    }

    buildDays(start, workoutDates) {
        let days = [];
        for (let i = 0; i < 7; i++) {
            let currDate = start.clone().add(i, "d");
            let clazz = this.inWorkoutDates(currDate, workoutDates) ? "workout" : "";
            days[i] = {
                clazz: clazz,
                date: currDate.date()
            };
        }
        return days;
    }

    inWorkoutDates(date, workoutDates) {
        if (workoutDates) {
            for (let wd of workoutDates) {
                if (date.isSame(wd)) {
                    return true;
                }
            }
        }
        return false;
    }
}

CalendarCtrl.$inject = ["WorkoutsService"];

export { CalendarCtrl };