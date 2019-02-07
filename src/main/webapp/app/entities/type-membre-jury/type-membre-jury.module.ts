import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeMembreJuryComponent,
    TypeMembreJuryDetailComponent,
    TypeMembreJuryUpdateComponent,
    TypeMembreJuryDeletePopupComponent,
    TypeMembreJuryDeleteDialogComponent,
    typeMembreJuryRoute,
    typeMembreJuryPopupRoute
} from './';

const ENTITY_STATES = [...typeMembreJuryRoute, ...typeMembreJuryPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeMembreJuryComponent,
        TypeMembreJuryDetailComponent,
        TypeMembreJuryUpdateComponent,
        TypeMembreJuryDeleteDialogComponent,
        TypeMembreJuryDeletePopupComponent
    ],
    entryComponents: [
        TypeMembreJuryComponent,
        TypeMembreJuryUpdateComponent,
        TypeMembreJuryDeleteDialogComponent,
        TypeMembreJuryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeMembreJuryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
