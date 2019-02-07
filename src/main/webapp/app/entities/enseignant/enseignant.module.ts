import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    EnseignantComponent,
    EnseignantDetailComponent,
    EnseignantUpdateComponent,
    EnseignantDeletePopupComponent,
    EnseignantDeleteDialogComponent,
    enseignantRoute,
    enseignantPopupRoute
} from './';

const ENTITY_STATES = [...enseignantRoute, ...enseignantPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnseignantComponent,
        EnseignantDetailComponent,
        EnseignantUpdateComponent,
        EnseignantDeleteDialogComponent,
        EnseignantDeletePopupComponent
    ],
    entryComponents: [EnseignantComponent, EnseignantUpdateComponent, EnseignantDeleteDialogComponent, EnseignantDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEnseignantModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
