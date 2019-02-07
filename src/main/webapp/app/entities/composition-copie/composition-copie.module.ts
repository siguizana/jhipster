import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CompositionCopieComponent,
    CompositionCopieDetailComponent,
    CompositionCopieUpdateComponent,
    CompositionCopieDeletePopupComponent,
    CompositionCopieDeleteDialogComponent,
    compositionCopieRoute,
    compositionCopiePopupRoute
} from './';

const ENTITY_STATES = [...compositionCopieRoute, ...compositionCopiePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompositionCopieComponent,
        CompositionCopieDetailComponent,
        CompositionCopieUpdateComponent,
        CompositionCopieDeleteDialogComponent,
        CompositionCopieDeletePopupComponent
    ],
    entryComponents: [
        CompositionCopieComponent,
        CompositionCopieUpdateComponent,
        CompositionCopieDeleteDialogComponent,
        CompositionCopieDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCompositionCopieModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
