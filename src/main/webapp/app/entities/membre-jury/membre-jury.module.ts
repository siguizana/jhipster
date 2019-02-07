import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    MembreJuryComponent,
    MembreJuryDetailComponent,
    MembreJuryUpdateComponent,
    MembreJuryDeletePopupComponent,
    MembreJuryDeleteDialogComponent,
    membreJuryRoute,
    membreJuryPopupRoute
} from './';

const ENTITY_STATES = [...membreJuryRoute, ...membreJuryPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MembreJuryComponent,
        MembreJuryDetailComponent,
        MembreJuryUpdateComponent,
        MembreJuryDeleteDialogComponent,
        MembreJuryDeletePopupComponent
    ],
    entryComponents: [MembreJuryComponent, MembreJuryUpdateComponent, MembreJuryDeleteDialogComponent, MembreJuryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecMembreJuryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
