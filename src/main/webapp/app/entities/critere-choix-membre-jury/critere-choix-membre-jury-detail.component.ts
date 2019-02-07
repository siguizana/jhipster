import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';

@Component({
    selector: 'jhi-critere-choix-membre-jury-detail',
    templateUrl: './critere-choix-membre-jury-detail.component.html'
})
export class CritereChoixMembreJuryDetailComponent implements OnInit {
    critereChoixMembreJury: ICritereChoixMembreJury;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ critereChoixMembreJury }) => {
            this.critereChoixMembreJury = critereChoixMembreJury;
        });
    }

    previousState() {
        window.history.back();
    }
}
