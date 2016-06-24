"use strict";

import moment from "moment";

class CalendarCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.weeks = this.buildWeeks();
        this.update();
        this.workoutsService.addUpdateListener(this);
        this.selectedDate = undefined;
    };

    update() {
        this.workoutsService.getWorkoutDates().then(dates => {
            this.weeks = this.buildWeeks(dates)
        });
    }

    buildWeeks(workoutDates) {
        let numWeeks = 4;
        let weeks = [];
        let today = moment();
        var start = moment().startOf("week");
        for (let i = 0; i < numWeeks; i++) {
            let weekStart = start.clone().subtract(i, "w");
            weeks[numWeeks - i] = this.buildDays(weekStart, workoutDates, today);
        }
        return weeks;
    }

    buildDays(start, workoutDates, today) {
        let days = [];
        for (let i = 0; i < 7; i++) {
            let currDate = start.clone().add(i, "d");
            days[i] = {
                hasWorkouts: this.hasWorkouts(currDate, workoutDates),
                isToday: currDate.isSame(today, "day"),
                date: currDate.date()
            };
        }
        return days;
    }

    hasWorkouts(date, workoutDates) {
        if (workoutDates) {
            for (let wd of workoutDates) {
                if (date.isSame(wd)) {
                    return true;
                }
            }
        }
        return false;
    }

    select(date) {
        this.selectedDate = date;
        this.workoutsService.getWorkoutsByDate(date);
    }
}

CalendarCtrl.$inject = ["WorkoutsService"];

export { CalendarCtrl };