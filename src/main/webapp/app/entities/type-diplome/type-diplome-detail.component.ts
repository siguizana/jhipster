import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeDiplome } from 'app/shared/model/type-diplome.model';

@Component({
    selector: 'jhi-type-diplome-detail',
    templateUrl: './type-diplome-detail.component.html'
})
export class TypeDiplomeDetailComponent implements OnInit {
    typeDiplome: ITypeDiplome;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeDiplome }) => {
            this.typeDiplome = typeDiplome;
        });
    }

    previousState() {
        window.history.back();
    }
}
