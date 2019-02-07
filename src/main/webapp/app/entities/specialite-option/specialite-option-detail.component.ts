import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';

@Component({
    selector: 'jhi-specialite-option-detail',
    templateUrl: './specialite-option-detail.component.html'
})
export class SpecialiteOptionDetailComponent implements OnInit {
    specialiteOption: ISpecialiteOption;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ specialiteOption }) => {
            this.specialiteOption = specialiteOption;
        });
    }

    previousState() {
        window.history.back();
    }
}
