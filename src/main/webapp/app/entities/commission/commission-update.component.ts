import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';
import { IMembreJuryJury } from 'app/shared/model/membre-jury-jury.model';
import { MembreJuryJuryService } from 'app/entities/membre-jury-jury';

@Component({
    selector: 'jhi-commission-update',
    templateUrl: './commission-update.component.html'
})
export class CommissionUpdateComponent implements OnInit {
    commission: ICommission;
    isSaving: boolean;

    membrejuryjuries: IMembreJuryJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commissionService: CommissionService,
        protected membreJuryJuryService: MembreJuryJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commission }) => {
            this.commission = commission;
        });
        this.membreJuryJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMembreJuryJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMembreJuryJury[]>) => response.body)
            )
            .subscribe((res: IMembreJuryJury[]) => (this.membrejuryjuries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commission.id !== undefined) {
            this.subscribeToSaveResponse(this.commissionService.update(this.commission));
        } else {
            this.subscribeToSaveResponse(this.commissionService.create(this.commission));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommission>>) {
        result.subscribe((res: HttpResponse<ICommission>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMembreJuryJuryById(index: number, item: IMembreJuryJury) {
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
