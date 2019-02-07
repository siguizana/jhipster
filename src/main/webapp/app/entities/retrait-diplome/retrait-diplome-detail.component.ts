import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';

@Component({
    selector: 'jhi-retrait-diplome-detail',
    templateUrl: './retrait-diplome-detail.component.html'
})
export class RetraitDiplomeDetailComponent implements OnInit {
    retraitDiplome: IRetraitDiplome;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ retraitDiplome }) => {
            this.retraitDiplome = retraitDiplome;
        });
    }

    previousState() {
        window.history.back();
    }
}
