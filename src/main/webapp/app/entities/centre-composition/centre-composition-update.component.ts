import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICentreComposition } from 'app/shared/model/centre-composition.model';
import { CentreCompositionService } from './centre-composition.service';
import { ICeb } from 'app/shared/model/ceb.model';
import { CebService } from 'app/entities/ceb';
import { IZoneExamen } from 'app/shared/model/zone-examen.model';
import { ZoneExamenService } from 'app/entities/zone-examen';
import { ITypeCentreComposition } from 'app/shared/model/type-centre-composition.model';
import { TypeCentreCompositionService } from 'app/entities/type-centre-composition';

@Component({
    selector: 'jhi-centre-composition-update',
    templateUrl: './centre-composition-update.component.html'
})
export class CentreCompositionUpdateComponent implements OnInit {
    centreComposition: ICentreComposition;
    isSaving: boolean;

    cebs: ICeb[];

    zoneexamen: IZoneExamen[];

    typecentrecompositions: ITypeCentreComposition[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected centreCompositionService: CentreCompositionService,
        protected cebService: CebService,
        protected zoneExamenService: ZoneExamenService,
        protected typeCentreCompositionService: TypeCentreCompositionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ centreComposition }) => {
            this.centreComposition = centreComposition;
        });
        this.cebService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICeb[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICeb[]>) => response.body)
            )
            .subscribe((res: ICeb[]) => (this.cebs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.zoneExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IZoneExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<IZoneExamen[]>) => response.body)
            )
            .subscribe((res: IZoneExamen[]) => (this.zoneexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeCentreCompositionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeCentreComposition[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeCentreComposition[]>) => response.body)
            )
            .subscribe(
                (res: ITypeCentreComposition[]) => (this.typecentrecompositions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.centreComposition.id !== undefined) {
            this.subscribeToSaveResponse(this.centreCompositionService.update(this.centreComposition));
        } else {
            this.subscribeToSaveResponse(this.centreCompositionService.create(this.centreComposition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentreComposition>>) {
        result.subscribe((res: HttpResponse<ICentreComposition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCebById(index: number, item: ICeb) {
        return item.id;
    }

    trackZoneExamenById(index: number, item: IZoneExamen) {
        return item.id;
    }

    trackTypeCentreCompositionById(index: number, item: ITypeCentreComposition) {
        return item.id;
    }
}
