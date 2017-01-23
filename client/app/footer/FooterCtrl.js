"use strict";

class FooterCtrl {
    constructor(workoutsService) {
        var self = this;
        this.workoutsService = workoutsService;
        this.gitConfig = {commitIdAbbrev: "", buildTime: ""};
        this.workoutsService.getGitConfiguration().then(data => {
            self.gitConfig.commitIdAbbrev = data.commitIdAbbrev;
            self.gitConfig.buildTime = data.buildTime;
        });
    }
}

FooterCtrl.$inject = ["WorkoutsService"];

export {FooterCtrl};