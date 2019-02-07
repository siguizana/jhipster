import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    EpreuveSpecialiteOptionComponent,
    EpreuveSpecialiteOptionDetailComponent,
    EpreuveSpecialiteOptionUpdateComponent,
    EpreuveSpecialiteOptionDeletePopupComponent,
    EpreuveSpecialiteOptionDeleteDialogComponent,
    epreuveSpecialiteOptionRoute,
    epreuveSpecialiteOptionPopupRoute
} from './';

const ENTITY_STATES = [...epreuveSpecialiteOptionRoute, ...epreuveSpecialiteOptionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EpreuveSpecialiteOptionComponent,
        EpreuveSpecialiteOptionDetailComponent,
        EpreuveSpecialiteOptionUpdateComponent,
        EpreuveSpecialiteOptionDeleteDialogComponent,
        EpreuveSpecialiteOptionDeletePopupComponent
    ],
    entryComponents: [
        EpreuveSpecialiteOptionComponent,
        EpreuveSpecialiteOptionUpdateComponent,
        EpreuveSpecialiteOptionDeleteDialogComponent,
        EpreuveSpecialiteOptionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEpreuveSpecialiteOptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
