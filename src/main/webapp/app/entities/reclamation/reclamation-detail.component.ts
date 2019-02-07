import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReclamation } from 'app/shared/model/reclamation.model';

@Component({
    selector: 'jhi-reclamation-detail',
    templateUrl: './reclamation-detail.component.html'
})
export class ReclamationDetailComponent implements OnInit {
    reclamation: IReclamation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reclamation }) => {
            this.reclamation = reclamation;
        });
    }

    previousState() {
        window.history.back();
    }
}
