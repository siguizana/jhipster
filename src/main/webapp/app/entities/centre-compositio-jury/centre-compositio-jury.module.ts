import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CentreCompositioJuryComponent,
    CentreCompositioJuryDetailComponent,
    CentreCompositioJuryUpdateComponent,
    CentreCompositioJuryDeletePopupComponent,
    CentreCompositioJuryDeleteDialogComponent,
    centreCompositioJuryRoute,
    centreCompositioJuryPopupRoute
} from './';

const ENTITY_STATES = [...centreCompositioJuryRoute, ...centreCompositioJuryPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CentreCompositioJuryComponent,
        CentreCompositioJuryDetailComponent,
        CentreCompositioJuryUpdateComponent,
        CentreCompositioJuryDeleteDialogComponent,
        CentreCompositioJuryDeletePopupComponent
    ],
    entryComponents: [
        CentreCompositioJuryComponent,
        CentreCompositioJuryUpdateComponent,
        CentreCompositioJuryDeleteDialogComponent,
        CentreCompositioJuryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCentreCompositioJuryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
