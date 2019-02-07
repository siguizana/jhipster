import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICentreExamen } from 'app/shared/model/centre-examen.model';

@Component({
    selector: 'jhi-centre-examen-detail',
    templateUrl: './centre-examen-detail.component.html'
})
export class CentreExamenDetailComponent implements OnInit {
    centreExamen: ICentreExamen;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ centreExamen }) => {
            this.centreExamen = centreExamen;
        });
    }

    previousState() {
        window.history.back();
    }
}
