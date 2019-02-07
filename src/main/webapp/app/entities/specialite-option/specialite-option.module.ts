import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    SpecialiteOptionComponent,
    SpecialiteOptionDetailComponent,
    SpecialiteOptionUpdateComponent,
    SpecialiteOptionDeletePopupComponent,
    SpecialiteOptionDeleteDialogComponent,
    specialiteOptionRoute,
    specialiteOptionPopupRoute
} from './';

const ENTITY_STATES = [...specialiteOptionRoute, ...specialiteOptionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SpecialiteOptionComponent,
        SpecialiteOptionDetailComponent,
        SpecialiteOptionUpdateComponent,
        SpecialiteOptionDeleteDialogComponent,
        SpecialiteOptionDeletePopupComponent
    ],
    entryComponents: [
        SpecialiteOptionComponent,
        SpecialiteOptionUpdateComponent,
        SpecialiteOptionDeleteDialogComponent,
        SpecialiteOptionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecSpecialiteOptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
