import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJury } from 'app/shared/model/jury.model';

@Component({
    selector: 'jhi-jury-detail',
    templateUrl: './jury-detail.component.html'
})
export class JuryDetailComponent implements OnInit {
    jury: IJury;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jury }) => {
            this.jury = jury;
        });
    }

    previousState() {
        window.history.back();
    }
}
