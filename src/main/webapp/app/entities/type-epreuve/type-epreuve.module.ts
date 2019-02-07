import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeEpreuveComponent,
    TypeEpreuveDetailComponent,
    TypeEpreuveUpdateComponent,
    TypeEpreuveDeletePopupComponent,
    TypeEpreuveDeleteDialogComponent,
    typeEpreuveRoute,
    typeEpreuvePopupRoute
} from './';

const ENTITY_STATES = [...typeEpreuveRoute, ...typeEpreuvePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeEpreuveComponent,
        TypeEpreuveDetailComponent,
        TypeEpreuveUpdateComponent,
        TypeEpreuveDeleteDialogComponent,
        TypeEpreuveDeletePopupComponent
    ],
    entryComponents: [TypeEpreuveComponent, TypeEpreuveUpdateComponent, TypeEpreuveDeleteDialogComponent, TypeEpreuveDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeEpreuveModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
