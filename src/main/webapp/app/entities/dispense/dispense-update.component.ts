import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDispense } from 'app/shared/model/dispense.model';
import { DispenseService } from './dispense.service';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { EpreuveSpecialiteOptionService } from 'app/entities/epreuve-specialite-option';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';
import { IHandicape } from 'app/shared/model/handicape.model';
import { HandicapeService } from 'app/entities/handicape';

@Component({
    selector: 'jhi-dispense-update',
    templateUrl: './dispense-update.component.html'
})
export class DispenseUpdateComponent implements OnInit {
    dispense: IDispense;
    isSaving: boolean;

    epreuvespecialiteoptions: IEpreuveSpecialiteOption[];

    inscriptions: IInscription[];

    handicapes: IHandicape[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dispenseService: DispenseService,
        protected epreuveSpecialiteOptionService: EpreuveSpecialiteOptionService,
        protected inscriptionService: InscriptionService,
        protected handicapeService: HandicapeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dispense }) => {
            this.dispense = dispense;
        });
        this.epreuveSpecialiteOptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEpreuveSpecialiteOption[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEpreuveSpecialiteOption[]>) => response.body)
            )
            .subscribe(
                (res: IEpreuveSpecialiteOption[]) => (this.epreuvespecialiteoptions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.inscriptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInscription[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInscription[]>) => response.body)
            )
            .subscribe((res: IInscription[]) => (this.inscriptions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.handicapeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHandicape[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHandicape[]>) => response.body)
            )
            .subscribe((res: IHandicape[]) => (this.handicapes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dispense.id !== undefined) {
            this.subscribeToSaveResponse(this.dispenseService.update(this.dispense));
        } else {
            this.subscribeToSaveResponse(this.dispenseService.create(this.dispense));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispense>>) {
        result.subscribe((res: HttpResponse<IDispense>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEpreuveSpecialiteOptionById(index: number, item: IEpreuveSpecialiteOption) {
        return item.id;
    }

    trackInscriptionById(index: number, item: IInscription) {
        return item.id;
    }

    trackHandicapeById(index: number, item: IHandicape) {
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
