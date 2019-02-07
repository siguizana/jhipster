import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    NoteMembreCritereComponent,
    NoteMembreCritereDetailComponent,
    NoteMembreCritereUpdateComponent,
    NoteMembreCritereDeletePopupComponent,
    NoteMembreCritereDeleteDialogComponent,
    noteMembreCritereRoute,
    noteMembreCriterePopupRoute
} from './';

const ENTITY_STATES = [...noteMembreCritereRoute, ...noteMembreCriterePopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NoteMembreCritereComponent,
        NoteMembreCritereDetailComponent,
        NoteMembreCritereUpdateComponent,
        NoteMembreCritereDeleteDialogComponent,
        NoteMembreCritereDeletePopupComponent
    ],
    entryComponents: [
        NoteMembreCritereComponent,
        NoteMembreCritereUpdateComponent,
        NoteMembreCritereDeleteDialogComponent,
        NoteMembreCritereDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecNoteMembreCritereModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
