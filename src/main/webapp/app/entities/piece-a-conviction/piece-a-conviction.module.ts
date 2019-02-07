import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    PieceAConvictionComponent,
    PieceAConvictionDetailComponent,
    PieceAConvictionUpdateComponent,
    PieceAConvictionDeletePopupComponent,
    PieceAConvictionDeleteDialogComponent,
    pieceAConvictionRoute,
    pieceAConvictionPopupRoute
} from './';

const ENTITY_STATES = [...pieceAConvictionRoute, ...pieceAConvictionPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PieceAConvictionComponent,
        PieceAConvictionDetailComponent,
        PieceAConvictionUpdateComponent,
        PieceAConvictionDeleteDialogComponent,
        PieceAConvictionDeletePopupComponent
    ],
    entryComponents: [
        PieceAConvictionComponent,
        PieceAConvictionUpdateComponent,
        PieceAConvictionDeleteDialogComponent,
        PieceAConvictionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecPieceAConvictionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
