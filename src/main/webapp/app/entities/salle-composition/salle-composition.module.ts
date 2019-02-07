import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    SalleCompositionComponent,
    SalleCompositionDetailComponent,
    SalleCompositionUpdateComponent,
    SalleCompositionDeletePopupComponent,
    SalleCompositionDeleteDialogComponent,
    salleCompositionRoute,
    salleCompositionPopupRoute
} from './';

const ENTITY_STATES = [...salleCompositionRoute, ...salleCompositionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalleCompositionComponent,
        SalleCompositionDetailComponent,
        SalleCompositionUpdateComponent,
        SalleCompositionDeleteDialogComponent,
        SalleCompositionDeletePopupComponent
    ],
    entryComponents: [
        SalleCompositionComponent,
        SalleCompositionUpdateComponent,
        SalleCompositionDeleteDialogComponent,
        SalleCompositionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecSalleCompositionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
