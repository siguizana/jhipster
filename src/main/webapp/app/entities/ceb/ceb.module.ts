import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CebComponent,
    CebDetailComponent,
    CebUpdateComponent,
    CebDeletePopupComponent,
    CebDeleteDialogComponent,
    cebRoute,
    cebPopupRoute
} from './';

const ENTITY_STATES = [...cebRoute, ...cebPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CebComponent, CebDetailComponent, CebUpdateComponent, CebDeleteDialogComponent, CebDeletePopupComponent],
    entryComponents: [CebComponent, CebUpdateComponent, CebDeleteDialogComponent, CebDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCebModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
