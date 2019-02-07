import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CentreCompositionComponent,
    CentreCompositionDetailComponent,
    CentreCompositionUpdateComponent,
    CentreCompositionDeletePopupComponent,
    CentreCompositionDeleteDialogComponent,
    centreCompositionRoute,
    centreCompositionPopupRoute
} from './';

const ENTITY_STATES = [...centreCompositionRoute, ...centreCompositionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CentreCompositionComponent,
        CentreCompositionDetailComponent,
        CentreCompositionUpdateComponent,
        CentreCompositionDeleteDialogComponent,
        CentreCompositionDeletePopupComponent
    ],
    entryComponents: [
        CentreCompositionComponent,
        CentreCompositionUpdateComponent,
        CentreCompositionDeleteDialogComponent,
        CentreCompositionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCentreCompositionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
