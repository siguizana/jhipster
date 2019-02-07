import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeEtablissement } from 'app/shared/model/type-etablissement.model';

@Component({
    selector: 'jhi-type-etablissement-detail',
    templateUrl: './type-etablissement-detail.component.html'
})
export class TypeEtablissementDetailComponent implements OnInit {
    typeEtablissement: ITypeEtablissement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeEtablissement }) => {
            this.typeEtablissement = typeEtablissement;
        });
    }

    previousState() {
        window.history.back();
    }
}
