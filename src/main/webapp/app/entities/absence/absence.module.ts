import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    AbsenceComponent,
    AbsenceDetailComponent,
    AbsenceUpdateComponent,
    AbsenceDeletePopupComponent,
    AbsenceDeleteDialogComponent,
    absenceRoute,
    absencePopupRoute
} from './';

const ENTITY_STATES = [...absenceRoute, ...absencePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AbsenceComponent,
        AbsenceDetailComponent,
        AbsenceUpdateComponent,
        AbsenceDeleteDialogComponent,
        AbsenceDeletePopupComponent
    ],
    entryComponents: [AbsenceComponent, AbsenceUpdateComponent, AbsenceDeleteDialogComponent, AbsenceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecAbsenceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
