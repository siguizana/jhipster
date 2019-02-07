import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    DeroulementScolariteComponent,
    DeroulementScolariteDetailComponent,
    DeroulementScolariteUpdateComponent,
    DeroulementScolariteDeletePopupComponent,
    DeroulementScolariteDeleteDialogComponent,
    deroulementScolariteRoute,
    deroulementScolaritePopupRoute
} from './';

const ENTITY_STATES = [...deroulementScolariteRoute, ...deroulementScolaritePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeroulementScolariteComponent,
        DeroulementScolariteDetailComponent,
        DeroulementScolariteUpdateComponent,
        DeroulementScolariteDeleteDialogComponent,
        DeroulementScolariteDeletePopupComponent
    ],
    entryComponents: [
        DeroulementScolariteComponent,
        DeroulementScolariteUpdateComponent,
        DeroulementScolariteDeleteDialogComponent,
        DeroulementScolariteDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecDeroulementScolariteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
