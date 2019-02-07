import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDispense } from 'app/shared/model/dispense.model';

@Component({
    selector: 'jhi-dispense-detail',
    templateUrl: './dispense-detail.component.html'
})
export class DispenseDetailComponent implements OnInit {
    dispense: IDispense;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dispense }) => {
            this.dispense = dispense;
        });
    }

    previousState() {
        window.history.back();
    }
}
