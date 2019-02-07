import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';

@Component({
    selector: 'jhi-choix-etablissement-detail',
    templateUrl: './choix-etablissement-detail.component.html'
})
export class ChoixEtablissementDetailComponent implements OnInit {
    choixEtablissement: IChoixEtablissement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ choixEtablissement }) => {
            this.choixEtablissement = choixEtablissement;
        });
    }

    previousState() {
        window.history.back();
    }
}
