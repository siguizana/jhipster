import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';

@Component({
    selector: 'jhi-epreuve-specialite-option-detail',
    templateUrl: './epreuve-specialite-option-detail.component.html'
})
export class EpreuveSpecialiteOptionDetailComponent implements OnInit {
    epreuveSpecialiteOption: IEpreuveSpecialiteOption;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ epreuveSpecialiteOption }) => {
            this.epreuveSpecialiteOption = epreuveSpecialiteOption;
        });
    }

    previousState() {
        window.history.back();
    }
}
