import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFraude } from 'app/shared/model/fraude.model';

@Component({
    selector: 'jhi-fraude-detail',
    templateUrl: './fraude-detail.component.html'
})
export class FraudeDetailComponent implements OnInit {
    fraude: IFraude;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fraude }) => {
            this.fraude = fraude;
        });
    }

    previousState() {
        window.history.back();
    }
}
