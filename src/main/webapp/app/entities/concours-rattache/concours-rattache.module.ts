import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    ConcoursRattacheComponent,
    ConcoursRattacheDetailComponent,
    ConcoursRattacheUpdateComponent,
    ConcoursRattacheDeletePopupComponent,
    ConcoursRattacheDeleteDialogComponent,
    concoursRattacheRoute,
    concoursRattachePopupRoute
} from './';

const ENTITY_STATES = [...concoursRattacheRoute, ...concoursRattachePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConcoursRattacheComponent,
        ConcoursRattacheDetailComponent,
        ConcoursRattacheUpdateComponent,
        ConcoursRattacheDeleteDialogComponent,
        ConcoursRattacheDeletePopupComponent
    ],
    entryComponents: [
        ConcoursRattacheComponent,
        ConcoursRattacheUpdateComponent,
        ConcoursRattacheDeleteDialogComponent,
        ConcoursRattacheDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecConcoursRattacheModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
