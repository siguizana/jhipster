import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFraude } from 'app/shared/model/fraude.model';
import { FraudeService } from './fraude.service';
import { ITypeFraude } from 'app/shared/model/type-fraude.model';
import { TypeFraudeService } from 'app/entities/type-fraude';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';
import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from 'app/entities/membre-jury';

@Component({
    selector: 'jhi-fraude-update',
    templateUrl: './fraude-update.component.html'
})
export class FraudeUpdateComponent implements OnInit {
    fraude: IFraude;
    isSaving: boolean;

    typefraudes: ITypeFraude[];

    inscriptions: IInscription[];

    membrejuries: IMembreJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected fraudeService: FraudeService,
        protected typeFraudeService: TypeFraudeService,
        protected inscriptionService: InscriptionService,
        protected membreJuryService: MembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fraude }) => {
            this.fraude = fraude;
        });
        this.typeFraudeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeFraude[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeFraude[]>) => response.body)
            )
            .subscribe((res: ITypeFraude[]) => (this.typefraudes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.inscriptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInscription[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInscription[]>) => response.body)
            )
            .subscribe((res: IInscription[]) => (this.inscriptions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.fraude.id !== undefined) {
            this.subscribeToSaveResponse(this.fraudeService.update(this.fraude));
        } else {
            this.subscribeToSaveResponse(this.fraudeService.create(this.fraude));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFraude>>) {
        result.subscribe((res: HttpResponse<IFraude>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeFraudeById(index: number, item: ITypeFraude) {
        return item.id;
    }

    trackInscriptionById(index: number, item: IInscription) {
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
