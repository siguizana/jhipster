import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    SanctionComponent,
    SanctionDetailComponent,
    SanctionUpdateComponent,
    SanctionDeletePopupComponent,
    SanctionDeleteDialogComponent,
    sanctionRoute,
    sanctionPopupRoute
} from './';

const ENTITY_STATES = [...sanctionRoute, ...sanctionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SanctionComponent,
        SanctionDetailComponent,
        SanctionUpdateComponent,
        SanctionDeleteDialogComponent,
        SanctionDeletePopupComponent
    ],
    entryComponents: [SanctionComponent, SanctionUpdateComponent, SanctionDeleteDialogComponent, SanctionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecSanctionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
