import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';

@Component({
    selector: 'jhi-deroulement-scolarite-detail',
    templateUrl: './deroulement-scolarite-detail.component.html'
})
export class DeroulementScolariteDetailComponent implements OnInit {
    deroulementScolarite: IDeroulementScolarite;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deroulementScolarite }) => {
            this.deroulementScolarite = deroulementScolarite;
        });
    }

    previousState() {
        window.history.back();
    }
}
