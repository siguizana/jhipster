import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CentreExamenComponent,
    CentreExamenDetailComponent,
    CentreExamenUpdateComponent,
    CentreExamenDeletePopupComponent,
    CentreExamenDeleteDialogComponent,
    centreExamenRoute,
    centreExamenPopupRoute
} from './';

const ENTITY_STATES = [...centreExamenRoute, ...centreExamenPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CentreExamenComponent,
        CentreExamenDetailComponent,
        CentreExamenUpdateComponent,
        CentreExamenDeleteDialogComponent,
        CentreExamenDeletePopupComponent
    ],
    entryComponents: [
        CentreExamenComponent,
        CentreExamenUpdateComponent,
        CentreExamenDeleteDialogComponent,
        CentreExamenDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCentreExamenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
