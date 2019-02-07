import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';

@Component({
    selector: 'jhi-membre-jury-jury-detail',
    templateUrl: './membre-jury-jury-detail.component.html'
})
export class MembreJuryJuryDetailComponent implements OnInit {
    membreJuryJury: IMembreJuryJury;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ membreJuryJury }) => {
            this.membreJuryJury = membreJuryJury;
        });
    }

    previousState() {
        window.history.back();
    }
}
