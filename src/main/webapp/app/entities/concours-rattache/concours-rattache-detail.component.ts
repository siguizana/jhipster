import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcoursRattache } from 'app/shared/model/concours-rattache.model';

@Component({
    selector: 'jhi-concours-rattache-detail',
    templateUrl: './concours-rattache-detail.component.html'
})
export class ConcoursRattacheDetailComponent implements OnInit {
    concoursRattache: IConcoursRattache;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ concoursRattache }) => {
            this.concoursRattache = concoursRattache;
        });
    }

    previousState() {
        window.history.back();
    }
}
