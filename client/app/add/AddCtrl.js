"use strict";

import moment from "moment";

class AddCtrl {
    constructor(workoutsService, $window) {
        this.workoutsService = workoutsService;
        this.$window = $window;
        this.workouts = [];
        this.types = [];
        this.newEntryDate = new Date();

        workoutsService.getAllTypes().then(data => this.types = data.sort());
    }

    add() {
        let newEntry = {
            type: this.newEntryType,
            duration: "PT" + this.newEntryDuration + "M",
            rating: this.newEntryRating,
            date: moment(this.newEntryDate).format("YYYY-MM-DD"),
            comment: this.newEntryComment
        };
        this.workoutsService.addEntry(newEntry).then(w => {
            this.$window.location.href = "/";
        });
    }
}

AddCtrl.$inject = ["WorkoutsService", "$window"];

export {AddCtrl}