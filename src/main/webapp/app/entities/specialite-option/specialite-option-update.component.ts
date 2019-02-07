import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';
import { SpecialiteOptionService } from './specialite-option.service';
import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from 'app/entities/type-examen';
import { IFiliere } from 'app/shared/model/filiere.model';
import { FiliereService } from 'app/entities/filiere';

@Component({
    selector: 'jhi-specialite-option-update',
    templateUrl: './specialite-option-update.component.html'
})
export class SpecialiteOptionUpdateComponent implements OnInit {
    specialiteOption: ISpecialiteOption;
    isSaving: boolean;

    typeexamen: ITypeExamen[];

    filieres: IFiliere[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected specialiteOptionService: SpecialiteOptionService,
        protected typeExamenService: TypeExamenService,
        protected filiereService: FiliereService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ specialiteOption }) => {
            this.specialiteOption = specialiteOption;
        });
        this.typeExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExamen[]>) => response.body)
            )
            .subscribe((res: ITypeExamen[]) => (this.typeexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.filiereService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFiliere[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFiliere[]>) => response.body)
            )
            .subscribe((res: IFiliere[]) => (this.filieres = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.specialiteOption.id !== undefined) {
            this.subscribeToSaveResponse(this.specialiteOptionService.update(this.specialiteOption));
        } else {
            this.subscribeToSaveResponse(this.specialiteOptionService.create(this.specialiteOption));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialiteOption>>) {
        result.subscribe((res: HttpResponse<ISpecialiteOption>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeExamenById(index: number, item: ITypeExamen) {
        return item.id;
    }

    trackFiliereById(index: number, item: IFiliere) {
        return item.id;
    }
}
