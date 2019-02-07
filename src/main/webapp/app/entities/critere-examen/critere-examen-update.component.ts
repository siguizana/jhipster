import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICritereExamen } from 'app/shared/model/critere-examen.model';
import { CritereExamenService } from './critere-examen.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region';
import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';
import { OptionConcoursRattacheService } from 'app/entities/option-concours-rattache';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';
import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from 'app/entities/type-examen';
import { ITypeCritere } from 'app/shared/model/type-critere.model';
import { TypeCritereService } from 'app/entities/type-critere';

@Component({
    selector: 'jhi-critere-examen-update',
    templateUrl: './critere-examen-update.component.html'
})
export class CritereExamenUpdateComponent implements OnInit {
    critereExamen: ICritereExamen;
    isSaving: boolean;

    regions: IRegion[];

    optionconcoursrattaches: IOptionConcoursRattache[];

    sessions: ISession[];

    typeexamen: ITypeExamen[];

    typecriteres: ITypeCritere[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected critereExamenService: CritereExamenService,
        protected regionService: RegionService,
        protected optionConcoursRattacheService: OptionConcoursRattacheService,
        protected sessionService: SessionService,
        protected typeExamenService: TypeExamenService,
        protected typeCritereService: TypeCritereService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ critereExamen }) => {
            this.critereExamen = critereExamen;
        });
        this.regionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRegion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRegion[]>) => response.body)
            )
            .subscribe((res: IRegion[]) => (this.regions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.optionConcoursRattacheService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOptionConcoursRattache[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOptionConcoursRattache[]>) => response.body)
            )
            .subscribe(
                (res: IOptionConcoursRattache[]) => (this.optionconcoursrattaches = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.sessionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISession[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISession[]>) => response.body)
            )
            .subscribe((res: ISession[]) => (this.sessions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExamen[]>) => response.body)
            )
            .subscribe((res: ITypeExamen[]) => (this.typeexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeCritereService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeCritere[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeCritere[]>) => response.body)
            )
            .subscribe((res: ITypeCritere[]) => (this.typecriteres = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.critereExamen.id !== undefined) {
            this.subscribeToSaveResponse(this.critereExamenService.update(this.critereExamen));
        } else {
            this.subscribeToSaveResponse(this.critereExamenService.create(this.critereExamen));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICritereExamen>>) {
        result.subscribe((res: HttpResponse<ICritereExamen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRegionById(index: number, item: IRegion) {
        return item.id;
    }

    trackOptionConcoursRattacheById(index: number, item: IOptionConcoursRattache) {
        return item.id;
    }

    trackSessionById(index: number, item: ISession) {
        return item.id;
    }

    trackTypeExamenById(index: number, item: ITypeExamen) {
        return item.id;
    }

    trackTypeCritereById(index: number, item: ITypeCritere) {
        return item.id;
    }
}
