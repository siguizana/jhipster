import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeDiplomeComponent,
    TypeDiplomeDetailComponent,
    TypeDiplomeUpdateComponent,
    TypeDiplomeDeletePopupComponent,
    TypeDiplomeDeleteDialogComponent,
    typeDiplomeRoute,
    typeDiplomePopupRoute
} from './';

const ENTITY_STATES = [...typeDiplomeRoute, ...typeDiplomePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeDiplomeComponent,
        TypeDiplomeDetailComponent,
        TypeDiplomeUpdateComponent,
        TypeDiplomeDeleteDialogComponent,
        TypeDiplomeDeletePopupComponent
    ],
    entryComponents: [TypeDiplomeComponent, TypeDiplomeUpdateComponent, TypeDiplomeDeleteDialogComponent, TypeDiplomeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeDiplomeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
