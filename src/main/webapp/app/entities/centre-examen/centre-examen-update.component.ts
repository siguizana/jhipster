import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICentreExamen } from 'app/shared/model/centre-examen.model';
import { CentreExamenService } from './centre-examen.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region';

@Component({
    selector: 'jhi-centre-examen-update',
    templateUrl: './centre-examen-update.component.html'
})
export class CentreExamenUpdateComponent implements OnInit {
    centreExamen: ICentreExamen;
    isSaving: boolean;

    regions: IRegion[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected centreExamenService: CentreExamenService,
        protected regionService: RegionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ centreExamen }) => {
            this.centreExamen = centreExamen;
        });
        this.regionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRegion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRegion[]>) => response.body)
            )
            .subscribe((res: IRegion[]) => (this.regions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.centreExamen.id !== undefined) {
            this.subscribeToSaveResponse(this.centreExamenService.update(this.centreExamen));
        } else {
            this.subscribeToSaveResponse(this.centreExamenService.create(this.centreExamen));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentreExamen>>) {
        result.subscribe((res: HttpResponse<ICentreExamen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRegionById(index: number, item: IRegion) {
        return item.id;
    }
}
