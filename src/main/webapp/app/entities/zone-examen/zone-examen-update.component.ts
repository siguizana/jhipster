import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IZoneExamen } from 'app/shared/model/zone-examen.model';
import { ZoneExamenService } from './zone-examen.service';
import { ICentreExamen } from 'app/shared/model/centre-examen.model';
import { CentreExamenService } from 'app/entities/centre-examen';

@Component({
    selector: 'jhi-zone-examen-update',
    templateUrl: './zone-examen-update.component.html'
})
export class ZoneExamenUpdateComponent implements OnInit {
    zoneExamen: IZoneExamen;
    isSaving: boolean;

    centreexamen: ICentreExamen[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected zoneExamenService: ZoneExamenService,
        protected centreExamenService: CentreExamenService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ zoneExamen }) => {
            this.zoneExamen = zoneExamen;
        });
        this.centreExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICentreExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICentreExamen[]>) => response.body)
            )
            .subscribe((res: ICentreExamen[]) => (this.centreexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.zoneExamen.id !== undefined) {
            this.subscribeToSaveResponse(this.zoneExamenService.update(this.zoneExamen));
        } else {
            this.subscribeToSaveResponse(this.zoneExamenService.create(this.zoneExamen));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IZoneExamen>>) {
        result.subscribe((res: HttpResponse<IZoneExamen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCentreExamenById(index: number, item: ICentreExamen) {
        return item.id;
    }
}
