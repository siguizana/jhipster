import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalleComposition } from 'app/shared/model/salle-composition.model';

@Component({
    selector: 'jhi-salle-composition-detail',
    templateUrl: './salle-composition-detail.component.html'
})
export class SalleCompositionDetailComponent implements OnInit {
    salleComposition: ISalleComposition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salleComposition }) => {
            this.salleComposition = salleComposition;
        });
    }

    previousState() {
        window.history.back();
    }
}
