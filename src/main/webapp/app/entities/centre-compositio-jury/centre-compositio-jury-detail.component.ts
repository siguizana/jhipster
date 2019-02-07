import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';

@Component({
    selector: 'jhi-centre-compositio-jury-detail',
    templateUrl: './centre-compositio-jury-detail.component.html'
})
export class CentreCompositioJuryDetailComponent implements OnInit {
    centreCompositioJury: ICentreCompositioJury;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ centreCompositioJury }) => {
            this.centreCompositioJury = centreCompositioJury;
        });
    }

    previousState() {
        window.history.back();
    }
}
