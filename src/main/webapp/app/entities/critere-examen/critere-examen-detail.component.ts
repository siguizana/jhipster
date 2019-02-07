import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICritereExamen } from 'app/shared/model/critere-examen.model';

@Component({
    selector: 'jhi-critere-examen-detail',
    templateUrl: './critere-examen-detail.component.html'
})
export class CritereExamenDetailComponent implements OnInit {
    critereExamen: ICritereExamen;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ critereExamen }) => {
            this.critereExamen = critereExamen;
        });
    }

    previousState() {
        window.history.back();
    }
}
