import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    HandicapeComponent,
    HandicapeDetailComponent,
    HandicapeUpdateComponent,
    HandicapeDeletePopupComponent,
    HandicapeDeleteDialogComponent,
    handicapeRoute,
    handicapePopupRoute
} from './';

const ENTITY_STATES = [...handicapeRoute, ...handicapePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HandicapeComponent,
        HandicapeDetailComponent,
        HandicapeUpdateComponent,
        HandicapeDeleteDialogComponent,
        HandicapeDeletePopupComponent
    ],
    entryComponents: [HandicapeComponent, HandicapeUpdateComponent, HandicapeDeleteDialogComponent, HandicapeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecHandicapeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
