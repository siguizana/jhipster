import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtapeExamen } from 'app/shared/model/etape-examen.model';

@Component({
    selector: 'jhi-etape-examen-detail',
    templateUrl: './etape-examen-detail.component.html'
})
export class EtapeExamenDetailComponent implements OnInit {
    etapeExamen: IEtapeExamen;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ etapeExamen }) => {
            this.etapeExamen = etapeExamen;
        });
    }

    previousState() {
        window.history.back();
    }
}
