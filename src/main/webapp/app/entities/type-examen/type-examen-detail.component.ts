import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeExamen } from 'app/shared/model/type-examen.model';

@Component({
    selector: 'jhi-type-examen-detail',
    templateUrl: './type-examen-detail.component.html'
})
export class TypeExamenDetailComponent implements OnInit {
    typeExamen: ITypeExamen;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeExamen }) => {
            this.typeExamen = typeExamen;
        });
    }

    previousState() {
        window.history.back();
    }
}
