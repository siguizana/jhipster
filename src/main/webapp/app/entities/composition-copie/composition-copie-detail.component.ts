import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompositionCopie } from 'app/shared/model/composition-copie.model';

@Component({
    selector: 'jhi-composition-copie-detail',
    templateUrl: './composition-copie-detail.component.html'
})
export class CompositionCopieDetailComponent implements OnInit {
    compositionCopie: ICompositionCopie;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compositionCopie }) => {
            this.compositionCopie = compositionCopie;
        });
    }

    previousState() {
        window.history.back();
    }
}
