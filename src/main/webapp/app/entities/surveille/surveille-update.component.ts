import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISurveille } from 'app/shared/model/surveille.model';
import { SurveilleService } from './surveille.service';
import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from 'app/entities/membre-jury';
import { ISalleComposition } from 'app/shared/model/salle-composition.model';
import { SalleCompositionService } from 'app/entities/salle-composition';

@Component({
    selector: 'jhi-surveille-update',
    templateUrl: './surveille-update.component.html'
})
export class SurveilleUpdateComponent implements OnInit {
    surveille: ISurveille;
    isSaving: boolean;

    membrejuries: IMembreJury[];

    sallecompositions: ISalleComposition[];
    dateDebutSurveillance: string;
    dateFinSurveillance: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected surveilleService: SurveilleService,
        protected membreJuryService: MembreJuryService,
        protected salleCompositionService: SalleCompositionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ surveille }) => {
            this.surveille = surveille;
            this.dateDebutSurveillance =
                this.surveille.dateDebutSurveillance != null ? this.surveille.dateDebutSurveillance.format(DATE_TIME_FORMAT) : null;
            this.dateFinSurveillance =
                this.surveille.dateFinSurveillance != null ? this.surveille.dateFinSurveillance.format(DATE_TIME_FORMAT) : null;
        });
        this.membreJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMembreJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMembreJury[]>) => response.body)
            )
            .subscribe((res: IMembreJury[]) => (this.membrejuries = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.salleCompositionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISalleComposition[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISalleComposition[]>) => response.body)
            )
            .subscribe((res: ISalleComposition[]) => (this.sallecompositions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.surveille.dateDebutSurveillance =
            this.dateDebutSurveillance != null ? moment(this.dateDebutSurveillance, DATE_TIME_FORMAT) : null;
        this.surveille.dateFinSurveillance = this.dateFinSurveillance != null ? moment(this.dateFinSurveillance, DATE_TIME_FORMAT) : null;
        if (this.surveille.id !== undefined) {
            this.subscribeToSaveResponse(this.surveilleService.update(this.surveille));
        } else {
            this.subscribeToSaveResponse(this.surveilleService.create(this.surveille));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISurveille>>) {
        result.subscribe((res: HttpResponse<ISurveille>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMembreJuryById(index: number, item: IMembreJury) {
        return item.id;
    }

    trackSalleCompositionById(index: number, item: ISalleComposition) {
        return item.id;
    }
}
