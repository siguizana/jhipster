import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCentreComposition } from 'app/shared/model/type-centre-composition.model';

@Component({
    selector: 'jhi-type-centre-composition-detail',
    templateUrl: './type-centre-composition-detail.component.html'
})
export class TypeCentreCompositionDetailComponent implements OnInit {
    typeCentreComposition: ITypeCentreComposition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeCentreComposition }) => {
            this.typeCentreComposition = typeCentreComposition;
        });
    }

    previousState() {
        window.history.back();
    }
}
