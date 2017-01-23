"use strict";
import "./footer.html"

class FooterDirective {
    constructor() {
        this.templateUrl = "footer.html";
        this.restrict = "E";
        this.scope = {};
    }
}

export { FooterDirective }