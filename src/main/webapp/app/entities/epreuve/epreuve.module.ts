import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    EpreuveComponent,
    EpreuveDetailComponent,
    EpreuveUpdateComponent,
    EpreuveDeletePopupComponent,
    EpreuveDeleteDialogComponent,
    epreuveRoute,
    epreuvePopupRoute
} from './';

const ENTITY_STATES = [...epreuveRoute, ...epreuvePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EpreuveComponent,
        EpreuveDetailComponent,
        EpreuveUpdateComponent,
        EpreuveDeleteDialogComponent,
        EpreuveDeletePopupComponent
    ],
    entryComponents: [EpreuveComponent, EpreuveUpdateComponent, EpreuveDeleteDialogComponent, EpreuveDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEpreuveModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
