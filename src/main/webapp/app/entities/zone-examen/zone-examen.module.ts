import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SigecSharedModule } from 'app/shared';
import {
    ZoneExamenComponent,
    ZoneExamenDetailComponent,
    ZoneExamenUpdateComponent,
    ZoneExamenDeletePopupComponent,
    ZoneExamenDeleteDialogComponent,
    zoneExamenRoute,
    zoneExamenPopupRoute
} from './';

const ENTITY_STATES = [...zoneExamenRoute, ...zoneExamenPopupRoute];

@NgModule({
    imports: [SigecSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ZoneExamenComponent,
        ZoneExamenDetailComponent,
        ZoneExamenUpdateComponent,
        ZoneExamenDeleteDialogComponent,
        ZoneExamenDeletePopupComponent
    ],
    entryComponents: [ZoneExamenComponent, ZoneExamenUpdateComponent, ZoneExamenDeleteDialogComponent, ZoneExamenDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecZoneExamenModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
