import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { CompositionCopieService } from './composition-copie.service';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { EpreuveSpecialiteOptionService } from 'app/entities/epreuve-specialite-option';
import { IEtapeExamen } from 'app/shared/model/etape-examen.model';
import { EtapeExamenService } from 'app/entities/etape-examen';
import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from 'app/entities/membre-jury';

@Component({
    selector: 'jhi-composition-copie-update',
    templateUrl: './composition-copie-update.component.html'
})
export class CompositionCopieUpdateComponent implements OnInit {
    compositionCopie: ICompositionCopie;
    isSaving: boolean;

    inscriptions: IInscription[];

    epreuvespecialiteoptions: IEpreuveSpecialiteOption[];

    etapeexamen: IEtapeExamen[];

    membrejuries: IMembreJury[];
    dateCompositionDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected compositionCopieService: CompositionCopieService,
        protected inscriptionService: InscriptionService,
        protected epreuveSpecialiteOptionService: EpreuveSpecialiteOptionService,
        protected etapeExamenService: EtapeExamenService,
        protected membreJuryService: MembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compositionCopie }) => {
            this.compositionCopie = compositionCopie;
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
        if (this.compositionCopie.id !== undefined) {
            this.subscribeToSaveResponse(this.compositionCopieService.update(this.compositionCopie));
        } else {
            this.subscribeToSaveResponse(this.compositionCopieService.create(this.compositionCopie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompositionCopie>>) {
        result.subscribe((res: HttpResponse<ICompositionCopie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
