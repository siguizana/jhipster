import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRetraitDiplome } from 'app/shared/model/retrait-diplome.model';
import { RetraitDiplomeService } from './retrait-diplome.service';
import { IResultat } from 'app/shared/model/resultat.model';
import { ResultatService } from 'app/entities/resultat';
import { ITypeDiplome } from 'app/shared/model/type-diplome.model';
import { TypeDiplomeService } from 'app/entities/type-diplome';

@Component({
    selector: 'jhi-retrait-diplome-update',
    templateUrl: './retrait-diplome-update.component.html'
})
export class RetraitDiplomeUpdateComponent implements OnInit {
    retraitDiplome: IRetraitDiplome;
    isSaving: boolean;

    resultats: IResultat[];

    typediplomes: ITypeDiplome[];
    dateRetraitDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected retraitDiplomeService: RetraitDiplomeService,
        protected resultatService: ResultatService,
        protected typeDiplomeService: TypeDiplomeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ retraitDiplome }) => {
            this.retraitDiplome = retraitDiplome;
        });
        this.resultatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IResultat[]>) => mayBeOk.ok),
                map((response: HttpResponse<IResultat[]>) => response.body)
            )
            .subscribe((res: IResultat[]) => (this.resultats = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeDiplomeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeDiplome[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeDiplome[]>) => response.body)
            )
            .subscribe((res: ITypeDiplome[]) => (this.typediplomes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.retraitDiplome.id !== undefined) {
            this.subscribeToSaveResponse(this.retraitDiplomeService.update(this.retraitDiplome));
        } else {
            this.subscribeToSaveResponse(this.retraitDiplomeService.create(this.retraitDiplome));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRetraitDiplome>>) {
        result.subscribe((res: HttpResponse<IRetraitDiplome>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackResultatById(index: number, item: IResultat) {
        return item.id;
    }

    trackTypeDiplomeById(index: number, item: ITypeDiplome) {
        return item.id;
    }
}
