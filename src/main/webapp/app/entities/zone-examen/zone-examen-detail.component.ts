import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZoneExamen } from 'app/shared/model/zone-examen.model';

@Component({
    selector: 'jhi-zone-examen-detail',
    templateUrl: './zone-examen-detail.component.html'
})
export class ZoneExamenDetailComponent implements OnInit {
    zoneExamen: IZoneExamen;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zoneExamen }) => {
            this.zoneExamen = zoneExamen;
        });
    }

    previousState() {
        window.history.back();
    }
}
