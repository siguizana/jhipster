import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    EtablissementComponent,
    EtablissementDetailComponent,
    EtablissementUpdateComponent,
    EtablissementDeletePopupComponent,
    EtablissementDeleteDialogComponent,
    etablissementRoute,
    etablissementPopupRoute
} from './';

const ENTITY_STATES = [...etablissementRoute, ...etablissementPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EtablissementComponent,
        EtablissementDetailComponent,
        EtablissementUpdateComponent,
        EtablissementDeleteDialogComponent,
        EtablissementDeletePopupComponent
    ],
    entryComponents: [
        EtablissementComponent,
        EtablissementUpdateComponent,
        EtablissementDeleteDialogComponent,
        EtablissementDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEtablissementModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
