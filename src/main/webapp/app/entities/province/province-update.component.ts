import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProvince } from 'app/shared/model/province.model';
import { ProvinceService } from './province.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region';

@Component({
    selector: 'jhi-province-update',
    templateUrl: './province-update.component.html'
})
export class ProvinceUpdateComponent implements OnInit {
    province: IProvince;
    isSaving: boolean;

    regions: IRegion[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected provinceService: ProvinceService,
        protected regionService: RegionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ province }) => {
            this.province = province;
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
        if (this.province.id !== undefined) {
            this.subscribeToSaveResponse(this.provinceService.update(this.province));
        } else {
            this.subscribeToSaveResponse(this.provinceService.create(this.province));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvince>>) {
        result.subscribe((res: HttpResponse<IProvince>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
