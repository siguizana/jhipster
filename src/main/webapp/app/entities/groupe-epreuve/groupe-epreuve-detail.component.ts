import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';

@Component({
    selector: 'jhi-groupe-epreuve-detail',
    templateUrl: './groupe-epreuve-detail.component.html'
})
export class GroupeEpreuveDetailComponent implements OnInit {
    groupeEpreuve: IGroupeEpreuve;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupeEpreuve }) => {
            this.groupeEpreuve = groupeEpreuve;
        });
    }

    previousState() {
        window.history.back();
    }
}
