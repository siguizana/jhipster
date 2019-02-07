import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeEpreuve } from 'app/shared/model/type-epreuve.model';

@Component({
    selector: 'jhi-type-epreuve-detail',
    templateUrl: './type-epreuve-detail.component.html'
})
export class TypeEpreuveDetailComponent implements OnInit {
    typeEpreuve: ITypeEpreuve;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeEpreuve }) => {
            this.typeEpreuve = typeEpreuve;
        });
    }

    previousState() {
        window.history.back();
    }
}
