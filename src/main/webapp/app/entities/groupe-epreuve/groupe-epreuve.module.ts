import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    GroupeEpreuveComponent,
    GroupeEpreuveDetailComponent,
    GroupeEpreuveUpdateComponent,
    GroupeEpreuveDeletePopupComponent,
    GroupeEpreuveDeleteDialogComponent,
    groupeEpreuveRoute,
    groupeEpreuvePopupRoute
} from './';

const ENTITY_STATES = [...groupeEpreuveRoute, ...groupeEpreuvePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GroupeEpreuveComponent,
        GroupeEpreuveDetailComponent,
        GroupeEpreuveUpdateComponent,
        GroupeEpreuveDeleteDialogComponent,
        GroupeEpreuveDeletePopupComponent
    ],
    entryComponents: [
        GroupeEpreuveComponent,
        GroupeEpreuveUpdateComponent,
        GroupeEpreuveDeleteDialogComponent,
        GroupeEpreuveDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecGroupeEpreuveModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
