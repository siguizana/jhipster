import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISalleComposition } from 'app/shared/model/salle-composition.model';
import { SalleCompositionService } from './salle-composition.service';
import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { CentreCompositioJuryService } from 'app/entities/centre-compositio-jury';

@Component({
    selector: 'jhi-salle-composition-update',
    templateUrl: './salle-composition-update.component.html'
})
export class SalleCompositionUpdateComponent implements OnInit {
    salleComposition: ISalleComposition;
    isSaving: boolean;

    centrecompositiojuries: ICentreCompositioJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected salleCompositionService: SalleCompositionService,
        protected centreCompositioJuryService: CentreCompositioJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salleComposition }) => {
            this.salleComposition = salleComposition;
        });
        this.centreCompositioJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICentreCompositioJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICentreCompositioJury[]>) => response.body)
            )
            .subscribe(
                (res: ICentreCompositioJury[]) => (this.centrecompositiojuries = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.salleComposition.id !== undefined) {
            this.subscribeToSaveResponse(this.salleCompositionService.update(this.salleComposition));
        } else {
            this.subscribeToSaveResponse(this.salleCompositionService.create(this.salleComposition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalleComposition>>) {
        result.subscribe((res: HttpResponse<ISalleComposition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCentreCompositioJuryById(index: number, item: ICentreCompositioJury) {
        return item.id;
    }
}
