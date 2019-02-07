import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeCentreCompositionComponent,
    TypeCentreCompositionDetailComponent,
    TypeCentreCompositionUpdateComponent,
    TypeCentreCompositionDeletePopupComponent,
    TypeCentreCompositionDeleteDialogComponent,
    typeCentreCompositionRoute,
    typeCentreCompositionPopupRoute
} from './';

const ENTITY_STATES = [...typeCentreCompositionRoute, ...typeCentreCompositionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeCentreCompositionComponent,
        TypeCentreCompositionDetailComponent,
        TypeCentreCompositionUpdateComponent,
        TypeCentreCompositionDeleteDialogComponent,
        TypeCentreCompositionDeletePopupComponent
    ],
    entryComponents: [
        TypeCentreCompositionComponent,
        TypeCentreCompositionUpdateComponent,
        TypeCentreCompositionDeleteDialogComponent,
        TypeCentreCompositionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeCentreCompositionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
