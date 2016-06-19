"use strict";
import "./add.html";

class AddDirective {
    constructor() {
        this.templateUrl = "add.html";
        this.restrict = "E";
        this.scope = {};

    }
}

export { AddDirective };