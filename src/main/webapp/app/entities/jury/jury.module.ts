import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    JuryComponent,
    JuryDetailComponent,
    JuryUpdateComponent,
    JuryDeletePopupComponent,
    JuryDeleteDialogComponent,
    juryRoute,
    juryPopupRoute
} from './';

const ENTITY_STATES = [...juryRoute, ...juryPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [JuryComponent, JuryDetailComponent, JuryUpdateComponent, JuryDeleteDialogComponent, JuryDeletePopupComponent],
    entryComponents: [JuryComponent, JuryUpdateComponent, JuryDeleteDialogComponent, JuryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecJuryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
