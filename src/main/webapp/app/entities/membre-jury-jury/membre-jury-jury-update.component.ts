import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';
import { MembreJuryJuryService } from './membre-jury-jury.service';
import { ICommission } from 'app/shared/model/commission.model';
import { CommissionService } from 'app/entities/commission';
import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from 'app/entities/jury';
import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from 'app/entities/membre-jury';

@Component({
    selector: 'jhi-membre-jury-jury-update',
    templateUrl: './membre-jury-jury-update.component.html'
})
export class MembreJuryJuryUpdateComponent implements OnInit {
    membreJuryJury: IMembreJuryJury;
    isSaving: boolean;

    commissions: ICommission[];

    juries: IJury[];

    membrejuries: IMembreJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected membreJuryJuryService: MembreJuryJuryService,
        protected commissionService: CommissionService,
        protected juryService: JuryService,
        protected membreJuryService: MembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ membreJuryJury }) => {
            this.membreJuryJury = membreJuryJury;
        });
        this.commissionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommission[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommission[]>) => response.body)
            )
            .subscribe((res: ICommission[]) => (this.commissions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.juryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IJury[]>) => response.body)
            )
            .subscribe((res: IJury[]) => (this.juries = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.membreJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMembreJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMembreJury[]>) => response.body)
            )
            .subscribe((res: IMembreJury[]) => (this.membrejuries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.membreJuryJury.id !== undefined) {
            this.subscribeToSaveResponse(this.membreJuryJuryService.update(this.membreJuryJury));
        } else {
            this.subscribeToSaveResponse(this.membreJuryJuryService.create(this.membreJuryJury));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreJuryJury>>) {
        result.subscribe((res: HttpResponse<IMembreJuryJury>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommissionById(index: number, item: ICommission) {
        return item.id;
    }

    trackJuryById(index: number, item: IJury) {
        return item.id;
    }

    trackMembreJuryById(index: number, item: IMembreJury) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
