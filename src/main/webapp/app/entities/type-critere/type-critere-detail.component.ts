import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCritere } from 'app/shared/model/type-critere.model';

@Component({
    selector: 'jhi-type-critere-detail',
    templateUrl: './type-critere-detail.component.html'
})
export class TypeCritereDetailComponent implements OnInit {
    typeCritere: ITypeCritere;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeCritere }) => {
            this.typeCritere = typeCritere;
        });
    }

    previousState() {
        window.history.back();
    }
}
