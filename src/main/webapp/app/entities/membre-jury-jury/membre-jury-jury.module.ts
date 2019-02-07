import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    MembreJuryJuryComponent,
    MembreJuryJuryDetailComponent,
    MembreJuryJuryUpdateComponent,
    MembreJuryJuryDeletePopupComponent,
    MembreJuryJuryDeleteDialogComponent,
    membreJuryJuryRoute,
    membreJuryJuryPopupRoute
} from './';

const ENTITY_STATES = [...membreJuryJuryRoute, ...membreJuryJuryPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MembreJuryJuryComponent,
        MembreJuryJuryDetailComponent,
        MembreJuryJuryUpdateComponent,
        MembreJuryJuryDeleteDialogComponent,
        MembreJuryJuryDeletePopupComponent
    ],
    entryComponents: [
        MembreJuryJuryComponent,
        MembreJuryJuryUpdateComponent,
        MembreJuryJuryDeleteDialogComponent,
        MembreJuryJuryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecMembreJuryJuryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
