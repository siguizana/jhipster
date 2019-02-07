import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurveille } from 'app/shared/model/surveille.model';

@Component({
    selector: 'jhi-surveille-detail',
    templateUrl: './surveille-detail.component.html'
})
export class SurveilleDetailComponent implements OnInit {
    surveille: ISurveille;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ surveille }) => {
            this.surveille = surveille;
        });
    }

    previousState() {
        window.history.back();
    }
}
