import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeCritereComponent,
    TypeCritereDetailComponent,
    TypeCritereUpdateComponent,
    TypeCritereDeletePopupComponent,
    TypeCritereDeleteDialogComponent,
    typeCritereRoute,
    typeCriterePopupRoute
} from './';

const ENTITY_STATES = [...typeCritereRoute, ...typeCriterePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeCritereComponent,
        TypeCritereDetailComponent,
        TypeCritereUpdateComponent,
        TypeCritereDeleteDialogComponent,
        TypeCritereDeletePopupComponent
    ],
    entryComponents: [TypeCritereComponent, TypeCritereUpdateComponent, TypeCritereDeleteDialogComponent, TypeCritereDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeCritereModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
