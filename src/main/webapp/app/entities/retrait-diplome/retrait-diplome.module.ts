import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    RetraitDiplomeComponent,
    RetraitDiplomeDetailComponent,
    RetraitDiplomeUpdateComponent,
    RetraitDiplomeDeletePopupComponent,
    RetraitDiplomeDeleteDialogComponent,
    retraitDiplomeRoute,
    retraitDiplomePopupRoute
} from './';

const ENTITY_STATES = [...retraitDiplomeRoute, ...retraitDiplomePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RetraitDiplomeComponent,
        RetraitDiplomeDetailComponent,
        RetraitDiplomeUpdateComponent,
        RetraitDiplomeDeleteDialogComponent,
        RetraitDiplomeDeletePopupComponent
    ],
    entryComponents: [
        RetraitDiplomeComponent,
        RetraitDiplomeUpdateComponent,
        RetraitDiplomeDeleteDialogComponent,
        RetraitDiplomeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecRetraitDiplomeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
