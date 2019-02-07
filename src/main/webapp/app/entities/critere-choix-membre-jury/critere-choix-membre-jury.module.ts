import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CritereChoixMembreJuryComponent,
    CritereChoixMembreJuryDetailComponent,
    CritereChoixMembreJuryUpdateComponent,
    CritereChoixMembreJuryDeletePopupComponent,
    CritereChoixMembreJuryDeleteDialogComponent,
    critereChoixMembreJuryRoute,
    critereChoixMembreJuryPopupRoute
} from './';

const ENTITY_STATES = [...critereChoixMembreJuryRoute, ...critereChoixMembreJuryPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CritereChoixMembreJuryComponent,
        CritereChoixMembreJuryDetailComponent,
        CritereChoixMembreJuryUpdateComponent,
        CritereChoixMembreJuryDeleteDialogComponent,
        CritereChoixMembreJuryDeletePopupComponent
    ],
    entryComponents: [
        CritereChoixMembreJuryComponent,
        CritereChoixMembreJuryUpdateComponent,
        CritereChoixMembreJuryDeleteDialogComponent,
        CritereChoixMembreJuryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCritereChoixMembreJuryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
