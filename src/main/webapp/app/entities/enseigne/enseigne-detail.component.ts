import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnseigne } from 'app/shared/model/enseigne.model';

@Component({
    selector: 'jhi-enseigne-detail',
    templateUrl: './enseigne-detail.component.html'
})
export class EnseigneDetailComponent implements OnInit {
    enseigne: IEnseigne;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enseigne }) => {
            this.enseigne = enseigne;
        });
    }

    previousState() {
        window.history.back();
    }
}
