"use strict";

import moment from "moment";

class GarminCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.entries = [];
    }

    refreshEntries() {
        this.workoutsService.getGarminEntries().then(d => {
                this.entries = d;
                this.entries.forEach(m => m.start = m.start * 1000)
            }
        );
    }
}

GarminCtrl.$inject = ["WorkoutsService"];

export { GarminCtrl }