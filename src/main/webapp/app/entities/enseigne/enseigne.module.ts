import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    EnseigneComponent,
    EnseigneDetailComponent,
    EnseigneUpdateComponent,
    EnseigneDeletePopupComponent,
    EnseigneDeleteDialogComponent,
    enseigneRoute,
    enseignePopupRoute
} from './';

const ENTITY_STATES = [...enseigneRoute, ...enseignePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnseigneComponent,
        EnseigneDetailComponent,
        EnseigneUpdateComponent,
        EnseigneDeleteDialogComponent,
        EnseigneDeletePopupComponent
    ],
    entryComponents: [EnseigneComponent, EnseigneUpdateComponent, EnseigneDeleteDialogComponent, EnseigneDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEnseigneModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
