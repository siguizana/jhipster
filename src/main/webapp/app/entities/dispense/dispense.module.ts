import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    DispenseComponent,
    DispenseDetailComponent,
    DispenseUpdateComponent,
    DispenseDeletePopupComponent,
    DispenseDeleteDialogComponent,
    dispenseRoute,
    dispensePopupRoute
} from './';

const ENTITY_STATES = [...dispenseRoute, ...dispensePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DispenseComponent,
        DispenseDetailComponent,
        DispenseUpdateComponent,
        DispenseDeleteDialogComponent,
        DispenseDeletePopupComponent
    ],
    entryComponents: [DispenseComponent, DispenseUpdateComponent, DispenseDeleteDialogComponent, DispenseDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecDispenseModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
