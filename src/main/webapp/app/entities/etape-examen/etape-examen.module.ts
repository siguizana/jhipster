import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    EtapeExamenComponent,
    EtapeExamenDetailComponent,
    EtapeExamenUpdateComponent,
    EtapeExamenDeletePopupComponent,
    EtapeExamenDeleteDialogComponent,
    etapeExamenRoute,
    etapeExamenPopupRoute
} from './';

const ENTITY_STATES = [...etapeExamenRoute, ...etapeExamenPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EtapeExamenComponent,
        EtapeExamenDetailComponent,
        EtapeExamenUpdateComponent,
        EtapeExamenDeleteDialogComponent,
        EtapeExamenDeletePopupComponent
    ],
    entryComponents: [EtapeExamenComponent, EtapeExamenUpdateComponent, EtapeExamenDeleteDialogComponent, EtapeExamenDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEtapeExamenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
