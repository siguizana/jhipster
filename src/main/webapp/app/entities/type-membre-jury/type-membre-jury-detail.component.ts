import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';

@Component({
    selector: 'jhi-type-membre-jury-detail',
    templateUrl: './type-membre-jury-detail.component.html'
})
export class TypeMembreJuryDetailComponent implements OnInit {
    typeMembreJury: ITypeMembreJury;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeMembreJury }) => {
            this.typeMembreJury = typeMembreJury;
        });
    }

    previousState() {
        window.history.back();
    }
}
