import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    OptionConcoursRattacheComponent,
    OptionConcoursRattacheDetailComponent,
    OptionConcoursRattacheUpdateComponent,
    OptionConcoursRattacheDeletePopupComponent,
    OptionConcoursRattacheDeleteDialogComponent,
    optionConcoursRattacheRoute,
    optionConcoursRattachePopupRoute
} from './';

const ENTITY_STATES = [...optionConcoursRattacheRoute, ...optionConcoursRattachePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OptionConcoursRattacheComponent,
        OptionConcoursRattacheDetailComponent,
        OptionConcoursRattacheUpdateComponent,
        OptionConcoursRattacheDeleteDialogComponent,
        OptionConcoursRattacheDeletePopupComponent
    ],
    entryComponents: [
        OptionConcoursRattacheComponent,
        OptionConcoursRattacheUpdateComponent,
        OptionConcoursRattacheDeleteDialogComponent,
        OptionConcoursRattacheDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecOptionConcoursRattacheModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
