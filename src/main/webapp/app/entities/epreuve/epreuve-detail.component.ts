import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEpreuve } from 'app/shared/model/epreuve.model';

@Component({
    selector: 'jhi-epreuve-detail',
    templateUrl: './epreuve-detail.component.html'
})
export class EpreuveDetailComponent implements OnInit {
    epreuve: IEpreuve;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ epreuve }) => {
            this.epreuve = epreuve;
        });
    }

    previousState() {
        window.history.back();
    }
}
