import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    ChoixEtablissementComponent,
    ChoixEtablissementDetailComponent,
    ChoixEtablissementUpdateComponent,
    ChoixEtablissementDeletePopupComponent,
    ChoixEtablissementDeleteDialogComponent,
    choixEtablissementRoute,
    choixEtablissementPopupRoute
} from './';

const ENTITY_STATES = [...choixEtablissementRoute, ...choixEtablissementPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChoixEtablissementComponent,
        ChoixEtablissementDetailComponent,
        ChoixEtablissementUpdateComponent,
        ChoixEtablissementDeleteDialogComponent,
        ChoixEtablissementDeletePopupComponent
    ],
    entryComponents: [
        ChoixEtablissementComponent,
        ChoixEtablissementUpdateComponent,
        ChoixEtablissementDeleteDialogComponent,
        ChoixEtablissementDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecChoixEtablissementModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
