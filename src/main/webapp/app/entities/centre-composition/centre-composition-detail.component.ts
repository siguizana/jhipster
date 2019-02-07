import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICentreComposition } from 'app/shared/model/centre-composition.model';

@Component({
    selector: 'jhi-centre-composition-detail',
    templateUrl: './centre-composition-detail.component.html'
})
export class CentreCompositionDetailComponent implements OnInit {
    centreComposition: ICentreComposition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ centreComposition }) => {
            this.centreComposition = centreComposition;
        });
    }

    previousState() {
        window.history.back();
    }
}
