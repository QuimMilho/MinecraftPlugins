import {Router} from "express";

const router = Router();

export default router;

export class API {
    private name;
    private router;

    constructor(name) {
        this.name = name;
        this.router = Router();
    }

    getRouter() {
        return router;
    }

}