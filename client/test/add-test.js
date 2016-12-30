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
            addEntry(newEntry) {
                this.result = newEntry;
                return new Promise(function(){});
            }
        };
        $window = {};
        ctrl = new AddCtrl(workoutService, $window);
    });

    it("should be defined", function() {
        expect(ctrl).to.exist;
    });

    describe("when creating a new entry", function() {
        it("should set type", function() {
            ctrl.newEntryType = "TYPE";
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.type).to.equal("TYPE");
        });

        it("should set duration", function() {
            ctrl.newEntryDuration = 33;
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.duration).to.equal("PT33M");
        });

        it("should set comment", function() {
            ctrl.newEntryComment = "COMMENT";
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.comment).to.equal("COMMENT");
        });

        it("should set rating", function() {
            ctrl.newEntryRating = 5;
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.rating).to.equal(5);
        });

        it("should set workout date", function() {
            ctrl.newEntryDate = new Date(2016, 5, 24, 23, 12, 13);
            ctrl.add();
            let result = workoutService.result;
            expect(result).to.exist;
            expect(result.date).to.equal("2016-06-24");
        })
    });
});
