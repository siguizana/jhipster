import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVillageSecteur } from 'app/shared/model/village-secteur.model';

@Component({
    selector: 'jhi-village-secteur-detail',
    templateUrl: './village-secteur-detail.component.html'
})
export class VillageSecteurDetailComponent implements OnInit {
    villageSecteur: IVillageSecteur;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ villageSecteur }) => {
            this.villageSecteur = villageSecteur;
        });
    }

    previousState() {
        window.history.back();
    }
}
