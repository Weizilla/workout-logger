"use strict";

import moment from "moment";

class UpdateCtrl {
    constructor(workoutsService, $window) {
        this.workoutsService = workoutsService;
        this.$window = $window;
        this.workouts = [];
        this.types = [];
        this.newEntryDate = new Date();

        var id = [];
        var self = this;
        workoutsService.getEntry(id).then(data => {
            self.newEntryType = data.type;
            self.newEntryDuration = data.duration; //TODO parse
            self.newEntryRating = data.rating;
            self.newEntryDate = moment(data.date);
            self.newEntryComment = data.comment;
        });
    }

    update() {
        let newEntry = {
            type: this.newEntryType,
            duration: "PT" + this.newEntryDuration + "M",
            rating: this.newEntryRating,
            date: moment(this.newEntryDate).format("YYYY-MM-DD"),
            comment: this.newEntryComment
        };
        this.workoutsService.updateEntry(newEntry).then(w => {
            this.$window.location.href = "/";
        });
    }
}

UpdateCtrl.$inject = ["WorkoutsService", "$window"];

export {UpdateCtrl}