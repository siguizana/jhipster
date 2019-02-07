import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    SurveilleComponent,
    SurveilleDetailComponent,
    SurveilleUpdateComponent,
    SurveilleDeletePopupComponent,
    SurveilleDeleteDialogComponent,
    surveilleRoute,
    surveillePopupRoute
} from './';

const ENTITY_STATES = [...surveilleRoute, ...surveillePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SurveilleComponent,
        SurveilleDetailComponent,
        SurveilleUpdateComponent,
        SurveilleDeleteDialogComponent,
        SurveilleDeletePopupComponent
    ],
    entryComponents: [SurveilleComponent, SurveilleUpdateComponent, SurveilleDeleteDialogComponent, SurveilleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecSurveilleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
