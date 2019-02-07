import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeFraudeComponent,
    TypeFraudeDetailComponent,
    TypeFraudeUpdateComponent,
    TypeFraudeDeletePopupComponent,
    TypeFraudeDeleteDialogComponent,
    typeFraudeRoute,
    typeFraudePopupRoute
} from './';

const ENTITY_STATES = [...typeFraudeRoute, ...typeFraudePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeFraudeComponent,
        TypeFraudeDetailComponent,
        TypeFraudeUpdateComponent,
        TypeFraudeDeleteDialogComponent,
        TypeFraudeDeletePopupComponent
    ],
    entryComponents: [TypeFraudeComponent, TypeFraudeUpdateComponent, TypeFraudeDeleteDialogComponent, TypeFraudeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeFraudeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
