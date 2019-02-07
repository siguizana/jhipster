import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    FiliereComponent,
    FiliereDetailComponent,
    FiliereUpdateComponent,
    FiliereDeletePopupComponent,
    FiliereDeleteDialogComponent,
    filiereRoute,
    filierePopupRoute
} from './';

const ENTITY_STATES = [...filiereRoute, ...filierePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FiliereComponent,
        FiliereDetailComponent,
        FiliereUpdateComponent,
        FiliereDeleteDialogComponent,
        FiliereDeletePopupComponent
    ],
    entryComponents: [FiliereComponent, FiliereUpdateComponent, FiliereDeleteDialogComponent, FiliereDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecFiliereModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
