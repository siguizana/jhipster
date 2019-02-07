import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    InscriptionComponent,
    InscriptionDetailComponent,
    InscriptionUpdateComponent,
    InscriptionDeletePopupComponent,
    InscriptionDeleteDialogComponent,
    inscriptionRoute,
    inscriptionPopupRoute
} from './';

const ENTITY_STATES = [...inscriptionRoute, ...inscriptionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InscriptionComponent,
        InscriptionDetailComponent,
        InscriptionUpdateComponent,
        InscriptionDeleteDialogComponent,
        InscriptionDeletePopupComponent
    ],
    entryComponents: [InscriptionComponent, InscriptionUpdateComponent, InscriptionDeleteDialogComponent, InscriptionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecInscriptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
