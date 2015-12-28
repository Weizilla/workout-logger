"use strict";

import { WorkoutsCtrl } from "./WorkoutsCtrl";
import { WorkoutsService } from "./WorkoutsService"

let ctrl = WorkoutsCtrl;
let service = WorkoutsService.factory;

export { ctrl, service }