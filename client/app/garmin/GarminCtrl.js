"use strict";

class GarminCtrl {
    constructor(workoutsService) {
        this.workoutsService = workoutsService;
        this.entries = [];
        this.refreshEntries();
    }
    
    downloadEntries() {
        this.workoutsService.refreshGarminEntries().then(d => console.log("downloaded ", d.downloaded));
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