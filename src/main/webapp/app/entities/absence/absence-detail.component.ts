import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbsence } from 'app/shared/model/absence.model';

@Component({
    selector: 'jhi-absence-detail',
    templateUrl: './absence-detail.component.html'
})
export class AbsenceDetailComponent implements OnInit {
    absence: IAbsence;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ absence }) => {
            this.absence = absence;
        });
    }

    previousState() {
        window.history.back();
    }
}
