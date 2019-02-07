import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAbsence } from 'app/shared/model/absence.model';
import { AbsenceService } from './absence.service';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { EpreuveSpecialiteOptionService } from 'app/entities/epreuve-specialite-option';
import { IEtapeExamen } from 'app/shared/model/etape-examen.model';
import { EtapeExamenService } from 'app/entities/etape-examen';

@Component({
    selector: 'jhi-absence-update',
    templateUrl: './absence-update.component.html'
})
export class AbsenceUpdateComponent implements OnInit {
    absence: IAbsence;
    isSaving: boolean;

    inscriptions: IInscription[];

    epreuvespecialiteoptions: IEpreuveSpecialiteOption[];

    etapeexamen: IEtapeExamen[];
    dateAbsenceDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected absenceService: AbsenceService,
        protected inscriptionService: InscriptionService,
        protected epreuveSpecialiteOptionService: EpreuveSpecialiteOptionService,
        protected etapeExamenService: EtapeExamenService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ absence }) => {
            this.absence = absence;
        });
        this.inscriptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInscription[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInscription[]>) => response.body)
            )
            .subscribe((res: IInscription[]) => (this.inscriptions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.etapeExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEtapeExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEtapeExamen[]>) => response.body)
            )
            .subscribe((res: IEtapeExamen[]) => (this.etapeexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.absence.id !== undefined) {
            this.subscribeToSaveResponse(this.absenceService.update(this.absence));
        } else {
            this.subscribeToSaveResponse(this.absenceService.create(this.absence));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbsence>>) {
        result.subscribe((res: HttpResponse<IAbsence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackInscriptionById(index: number, item: IInscription) {
        return item.id;
    }

    trackEpreuveSpecialiteOptionById(index: number, item: IEpreuveSpecialiteOption) {
        return item.id;
    }

    trackEtapeExamenById(index: number, item: IEtapeExamen) {
        return item.id;
    }
}
