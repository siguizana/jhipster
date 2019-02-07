import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISanction } from 'app/shared/model/sanction.model';

@Component({
    selector: 'jhi-sanction-detail',
    templateUrl: './sanction-detail.component.html'
})
export class SanctionDetailComponent implements OnInit {
    sanction: ISanction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sanction }) => {
            this.sanction = sanction;
        });
    }

    previousState() {
        window.history.back();
    }
}
