import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';

@Component({
    selector: 'jhi-option-concours-rattache-detail',
    templateUrl: './option-concours-rattache-detail.component.html'
})
export class OptionConcoursRattacheDetailComponent implements OnInit {
    optionConcoursRattache: IOptionConcoursRattache;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ optionConcoursRattache }) => {
            this.optionConcoursRattache = optionConcoursRattache;
        });
    }

    previousState() {
        window.history.back();
    }
}
