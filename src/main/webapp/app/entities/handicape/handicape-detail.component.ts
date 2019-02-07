import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHandicape } from 'app/shared/model/handicape.model';

@Component({
    selector: 'jhi-handicape-detail',
    templateUrl: './handicape-detail.component.html'
})
export class HandicapeDetailComponent implements OnInit {
    handicape: IHandicape;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ handicape }) => {
            this.handicape = handicape;
        });
    }

    previousState() {
        window.history.back();
    }
}
