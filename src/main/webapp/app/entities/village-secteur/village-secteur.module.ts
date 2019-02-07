import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    VillageSecteurComponent,
    VillageSecteurDetailComponent,
    VillageSecteurUpdateComponent,
    VillageSecteurDeletePopupComponent,
    VillageSecteurDeleteDialogComponent,
    villageSecteurRoute,
    villageSecteurPopupRoute
} from './';

const ENTITY_STATES = [...villageSecteurRoute, ...villageSecteurPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VillageSecteurComponent,
        VillageSecteurDetailComponent,
        VillageSecteurUpdateComponent,
        VillageSecteurDeleteDialogComponent,
        VillageSecteurDeletePopupComponent
    ],
    entryComponents: [
        VillageSecteurComponent,
        VillageSecteurUpdateComponent,
        VillageSecteurDeleteDialogComponent,
        VillageSecteurDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecVillageSecteurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
