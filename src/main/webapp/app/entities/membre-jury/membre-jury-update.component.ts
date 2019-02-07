import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from './membre-jury.service';
import { IFraude } from 'app/shared/model/fraude.model';
import { FraudeService } from 'app/entities/fraude';
import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { CompositionCopieService } from 'app/entities/composition-copie';
import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';
import { TypeMembreJuryService } from 'app/entities/type-membre-jury';

@Component({
    selector: 'jhi-membre-jury-update',
    templateUrl: './membre-jury-update.component.html'
})
export class MembreJuryUpdateComponent implements OnInit {
    membreJury: IMembreJury;
    isSaving: boolean;

    fraudes: IFraude[];

    compositioncopies: ICompositionCopie[];

    typemembrejuries: ITypeMembreJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected membreJuryService: MembreJuryService,
        protected fraudeService: FraudeService,
        protected compositionCopieService: CompositionCopieService,
        protected typeMembreJuryService: TypeMembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ membreJury }) => {
            this.membreJury = membreJury;
        });
        this.fraudeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFraude[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFraude[]>) => response.body)
            )
            .subscribe((res: IFraude[]) => (this.fraudes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.compositionCopieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompositionCopie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompositionCopie[]>) => response.body)
            )
            .subscribe((res: ICompositionCopie[]) => (this.compositioncopies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeMembreJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeMembreJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeMembreJury[]>) => response.body)
            )
            .subscribe((res: ITypeMembreJury[]) => (this.typemembrejuries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.membreJury.id !== undefined) {
            this.subscribeToSaveResponse(this.membreJuryService.update(this.membreJury));
        } else {
            this.subscribeToSaveResponse(this.membreJuryService.create(this.membreJury));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreJury>>) {
        result.subscribe((res: HttpResponse<IMembreJury>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFraudeById(index: number, item: IFraude) {
        return item.id;
    }

    trackCompositionCopieById(index: number, item: ICompositionCopie) {
        return item.id;
    }

    trackTypeMembreJuryById(index: number, item: ITypeMembreJury) {
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
