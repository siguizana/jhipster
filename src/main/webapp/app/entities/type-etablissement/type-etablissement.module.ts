import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeEtablissementComponent,
    TypeEtablissementDetailComponent,
    TypeEtablissementUpdateComponent,
    TypeEtablissementDeletePopupComponent,
    TypeEtablissementDeleteDialogComponent,
    typeEtablissementRoute,
    typeEtablissementPopupRoute
} from './';

const ENTITY_STATES = [...typeEtablissementRoute, ...typeEtablissementPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeEtablissementComponent,
        TypeEtablissementDetailComponent,
        TypeEtablissementUpdateComponent,
        TypeEtablissementDeleteDialogComponent,
        TypeEtablissementDeletePopupComponent
    ],
    entryComponents: [
        TypeEtablissementComponent,
        TypeEtablissementUpdateComponent,
        TypeEtablissementDeleteDialogComponent,
        TypeEtablissementDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeEtablissementModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
