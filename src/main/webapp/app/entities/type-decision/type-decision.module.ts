import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeDecisionComponent,
    TypeDecisionDetailComponent,
    TypeDecisionUpdateComponent,
    TypeDecisionDeletePopupComponent,
    TypeDecisionDeleteDialogComponent,
    typeDecisionRoute,
    typeDecisionPopupRoute
} from './';

const ENTITY_STATES = [...typeDecisionRoute, ...typeDecisionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeDecisionComponent,
        TypeDecisionDetailComponent,
        TypeDecisionUpdateComponent,
        TypeDecisionDeleteDialogComponent,
        TypeDecisionDeletePopupComponent
    ],
    entryComponents: [
        TypeDecisionComponent,
        TypeDecisionUpdateComponent,
        TypeDecisionDeleteDialogComponent,
        TypeDecisionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeDecisionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
