import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeFraude } from 'app/shared/model/type-fraude.model';

@Component({
    selector: 'jhi-type-fraude-detail',
    templateUrl: './type-fraude-detail.component.html'
})
export class TypeFraudeDetailComponent implements OnInit {
    typeFraude: ITypeFraude;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeFraude }) => {
            this.typeFraude = typeFraude;
        });
    }

    previousState() {
        window.history.back();
    }
}
