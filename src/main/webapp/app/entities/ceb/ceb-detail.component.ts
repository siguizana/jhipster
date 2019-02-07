import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICeb } from 'app/shared/model/ceb.model';

@Component({
    selector: 'jhi-ceb-detail',
    templateUrl: './ceb-detail.component.html'
})
export class CebDetailComponent implements OnInit {
    ceb: ICeb;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ceb }) => {
            this.ceb = ceb;
        });
    }

    previousState() {
        window.history.back();
    }
}
