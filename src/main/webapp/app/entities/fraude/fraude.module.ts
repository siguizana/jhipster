import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    FraudeComponent,
    FraudeDetailComponent,
    FraudeUpdateComponent,
    FraudeDeletePopupComponent,
    FraudeDeleteDialogComponent,
    fraudeRoute,
    fraudePopupRoute
} from './';

const ENTITY_STATES = [...fraudeRoute, ...fraudePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FraudeComponent, FraudeDetailComponent, FraudeUpdateComponent, FraudeDeleteDialogComponent, FraudeDeletePopupComponent],
    entryComponents: [FraudeComponent, FraudeUpdateComponent, FraudeDeleteDialogComponent, FraudeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecFraudeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
