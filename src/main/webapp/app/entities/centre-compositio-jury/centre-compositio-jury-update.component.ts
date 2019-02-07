import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { CentreCompositioJuryService } from './centre-compositio-jury.service';
import { ICentreComposition } from 'app/shared/model/centre-composition.model';
import { CentreCompositionService } from 'app/entities/centre-composition';
import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from 'app/entities/jury';

@Component({
    selector: 'jhi-centre-compositio-jury-update',
    templateUrl: './centre-compositio-jury-update.component.html'
})
export class CentreCompositioJuryUpdateComponent implements OnInit {
    centreCompositioJury: ICentreCompositioJury;
    isSaving: boolean;

    centrecompositions: ICentreComposition[];

    juries: IJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected centreCompositioJuryService: CentreCompositioJuryService,
        protected centreCompositionService: CentreCompositionService,
        protected juryService: JuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ centreCompositioJury }) => {
            this.centreCompositioJury = centreCompositioJury;
        });
        this.centreCompositionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICentreComposition[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICentreComposition[]>) => response.body)
            )
            .subscribe(
                (res: ICentreComposition[]) => (this.centrecompositions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.juryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IJury[]>) => response.body)
            )
            .subscribe((res: IJury[]) => (this.juries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.centreCompositioJury.id !== undefined) {
            this.subscribeToSaveResponse(this.centreCompositioJuryService.update(this.centreCompositioJury));
        } else {
            this.subscribeToSaveResponse(this.centreCompositioJuryService.create(this.centreCompositioJury));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentreCompositioJury>>) {
        result.subscribe(
            (res: HttpResponse<ICentreCompositioJury>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackCentreCompositionById(index: number, item: ICentreComposition) {
        return item.id;
    }

    trackJuryById(index: number, item: IJury) {
        return item.id;
    }
}
