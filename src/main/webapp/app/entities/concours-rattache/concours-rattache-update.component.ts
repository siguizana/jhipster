import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IConcoursRattache } from 'app/shared/model/concours-rattache.model';
import { ConcoursRattacheService } from './concours-rattache.service';
import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from 'app/entities/type-examen';

@Component({
    selector: 'jhi-concours-rattache-update',
    templateUrl: './concours-rattache-update.component.html'
})
export class ConcoursRattacheUpdateComponent implements OnInit {
    concoursRattache: IConcoursRattache;
    isSaving: boolean;

    typeexamen: ITypeExamen[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected concoursRattacheService: ConcoursRattacheService,
        protected typeExamenService: TypeExamenService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ concoursRattache }) => {
            this.concoursRattache = concoursRattache;
        });
        this.typeExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExamen[]>) => response.body)
            )
            .subscribe((res: ITypeExamen[]) => (this.typeexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.concoursRattache.id !== undefined) {
            this.subscribeToSaveResponse(this.concoursRattacheService.update(this.concoursRattache));
        } else {
            this.subscribeToSaveResponse(this.concoursRattacheService.create(this.concoursRattache));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcoursRattache>>) {
        result.subscribe((res: HttpResponse<IConcoursRattache>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
