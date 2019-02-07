import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    MentionComponent,
    MentionDetailComponent,
    MentionUpdateComponent,
    MentionDeletePopupComponent,
    MentionDeleteDialogComponent,
    mentionRoute,
    mentionPopupRoute
} from './';

const ENTITY_STATES = [...mentionRoute, ...mentionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MentionComponent,
        MentionDetailComponent,
        MentionUpdateComponent,
        MentionDeleteDialogComponent,
        MentionDeletePopupComponent
    ],
    entryComponents: [MentionComponent, MentionUpdateComponent, MentionDeleteDialogComponent, MentionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecMentionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
