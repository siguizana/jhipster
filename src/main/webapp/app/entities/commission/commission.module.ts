import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    CommissionComponent,
    CommissionDetailComponent,
    CommissionUpdateComponent,
    CommissionDeletePopupComponent,
    CommissionDeleteDialogComponent,
    commissionRoute,
    commissionPopupRoute
} from './';

const ENTITY_STATES = [...commissionRoute, ...commissionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommissionComponent,
        CommissionDetailComponent,
        CommissionUpdateComponent,
        CommissionDeleteDialogComponent,
        CommissionDeletePopupComponent
    ],
    entryComponents: [CommissionComponent, CommissionUpdateComponent, CommissionDeleteDialogComponent, CommissionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecCommissionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
