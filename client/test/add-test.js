"use strict";

import { AddCtrl } from "../app/add/AddCtrl";
let expect = require("chai").expect;

describe("add controller", function() {
    let ctrl;
    let workoutService;
    let $window;

    beforeEach(function() {
        workoutService = {
            getAllTypes: function() {
                return new Promise(function(fulfill){fulfill([]);});
            },
            addWorkout(newWorkout) {
                this.result = newWorkout;
                return new Promise(function(){});
            }
        };
        $window = {};
        ctrl = new AddCtrl(workoutService, $window);
    });

    it("should be defined", function() {
        expect(ctrl).to.exist;
    });

    describe("when creating a new workout", function() {
        it("should set type", function() {
            ctrl.newWorkoutType = "TYPE";
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.type).to.equal("TYPE");
        });

        it("should set duration", function() {
            ctrl.newWorkoutDuration = 33;
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.duration).to.equal("PT33M");
        });

        it("should set comment", function() {
            ctrl.newWorkoutComment = "COMMENT";
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.comment).to.equal("COMMENT");
        });

        it("should set workout date", function() {
            ctrl.newWorkoutDate = new Date(2016, 5, 24, 23, 12, 13);
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.date).to.equal("2016-06-24");
        })
    });
});
