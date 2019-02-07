import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeDecision } from 'app/shared/model/type-decision.model';

@Component({
    selector: 'jhi-type-decision-detail',
    templateUrl: './type-decision-detail.component.html'
})
export class TypeDecisionDetailComponent implements OnInit {
    typeDecision: ITypeDecision;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeDecision }) => {
            this.typeDecision = typeDecision;
        });
    }

    previousState() {
        window.history.back();
    }
}
