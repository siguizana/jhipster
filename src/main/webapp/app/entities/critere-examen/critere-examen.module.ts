import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CritereExamenComponent,
    CritereExamenDetailComponent,
    CritereExamenUpdateComponent,
    CritereExamenDeletePopupComponent,
    CritereExamenDeleteDialogComponent,
    critereExamenRoute,
    critereExamenPopupRoute
} from './';

const ENTITY_STATES = [...critereExamenRoute, ...critereExamenPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CritereExamenComponent,
        CritereExamenDetailComponent,
        CritereExamenUpdateComponent,
        CritereExamenDeleteDialogComponent,
        CritereExamenDeletePopupComponent
    ],
    entryComponents: [
        CritereExamenComponent,
        CritereExamenUpdateComponent,
        CritereExamenDeleteDialogComponent,
        CritereExamenDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCritereExamenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
