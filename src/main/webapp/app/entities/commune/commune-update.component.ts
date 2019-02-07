import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommune } from 'app/shared/model/commune.model';
import { CommuneService } from './commune.service';
import { IProvince } from 'app/shared/model/province.model';
import { ProvinceService } from 'app/entities/province';

@Component({
    selector: 'jhi-commune-update',
    templateUrl: './commune-update.component.html'
})
export class CommuneUpdateComponent implements OnInit {
    commune: ICommune;
    isSaving: boolean;

    provinces: IProvince[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected communeService: CommuneService,
        protected provinceService: ProvinceService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commune }) => {
            this.commune = commune;
        });
        this.provinceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProvince[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProvince[]>) => response.body)
            )
            .subscribe((res: IProvince[]) => (this.provinces = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commune.id !== undefined) {
            this.subscribeToSaveResponse(this.communeService.update(this.commune));
        } else {
            this.subscribeToSaveResponse(this.communeService.create(this.commune));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommune>>) {
        result.subscribe((res: HttpResponse<ICommune>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProvinceById(index: number, item: IProvince) {
        return item.id;
    }
}
