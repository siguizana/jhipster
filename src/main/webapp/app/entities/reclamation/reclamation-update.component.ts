import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReclamation } from 'app/shared/model/reclamation.model';
import { ReclamationService } from './reclamation.service';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { CompositionCopieService } from 'app/entities/composition-copie';

@Component({
    selector: 'jhi-reclamation-update',
    templateUrl: './reclamation-update.component.html'
})
export class ReclamationUpdateComponent implements OnInit {
    reclamation: IReclamation;
    isSaving: boolean;

    compositioncopies: ICompositionCopie[];
    dateReclamationDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reclamationService: ReclamationService,
        protected compositionCopieService: CompositionCopieService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reclamation }) => {
            this.reclamation = reclamation;
        });
        this.compositionCopieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompositionCopie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompositionCopie[]>) => response.body)
            )
            .subscribe((res: ICompositionCopie[]) => (this.compositioncopies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reclamation.id !== undefined) {
            this.subscribeToSaveResponse(this.reclamationService.update(this.reclamation));
        } else {
            this.subscribeToSaveResponse(this.reclamationService.create(this.reclamation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReclamation>>) {
        result.subscribe((res: HttpResponse<IReclamation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompositionCopieById(index: number, item: ICompositionCopie) {
        return item.id;
    }
}
