import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEtapeExamen } from 'app/shared/model/etape-examen.model';
import { EtapeExamenService } from './etape-examen.service';
import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from 'app/entities/type-examen';

@Component({
    selector: 'jhi-etape-examen-update',
    templateUrl: './etape-examen-update.component.html'
})
export class EtapeExamenUpdateComponent implements OnInit {
    etapeExamen: IEtapeExamen;
    isSaving: boolean;

    typeexamen: ITypeExamen[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected etapeExamenService: EtapeExamenService,
        protected typeExamenService: TypeExamenService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ etapeExamen }) => {
            this.etapeExamen = etapeExamen;
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
        if (this.etapeExamen.id !== undefined) {
            this.subscribeToSaveResponse(this.etapeExamenService.update(this.etapeExamen));
        } else {
            this.subscribeToSaveResponse(this.etapeExamenService.create(this.etapeExamen));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtapeExamen>>) {
        result.subscribe((res: HttpResponse<IEtapeExamen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
