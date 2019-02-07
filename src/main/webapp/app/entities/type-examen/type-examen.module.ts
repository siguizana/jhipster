import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    TypeExamenComponent,
    TypeExamenDetailComponent,
    TypeExamenUpdateComponent,
    TypeExamenDeletePopupComponent,
    TypeExamenDeleteDialogComponent,
    typeExamenRoute,
    typeExamenPopupRoute
} from './';

const ENTITY_STATES = [...typeExamenRoute, ...typeExamenPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeExamenComponent,
        TypeExamenDetailComponent,
        TypeExamenUpdateComponent,
        TypeExamenDeleteDialogComponent,
        TypeExamenDeletePopupComponent
    ],
    entryComponents: [TypeExamenComponent, TypeExamenUpdateComponent, TypeExamenDeleteDialogComponent, TypeExamenDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecTypeExamenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
