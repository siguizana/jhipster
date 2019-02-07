import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEpreuve } from 'app/shared/model/epreuve.model';
import { EpreuveService } from './epreuve.service';
import { ITypeEpreuve } from 'app/shared/model/type-epreuve.model';
import { TypeEpreuveService } from 'app/entities/type-epreuve';

@Component({
    selector: 'jhi-epreuve-update',
    templateUrl: './epreuve-update.component.html'
})
export class EpreuveUpdateComponent implements OnInit {
    epreuve: IEpreuve;
    isSaving: boolean;

    typeepreuves: ITypeEpreuve[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected epreuveService: EpreuveService,
        protected typeEpreuveService: TypeEpreuveService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ epreuve }) => {
            this.epreuve = epreuve;
        });
        this.typeEpreuveService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeEpreuve[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeEpreuve[]>) => response.body)
            )
            .subscribe((res: ITypeEpreuve[]) => (this.typeepreuves = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.epreuve.id !== undefined) {
            this.subscribeToSaveResponse(this.epreuveService.update(this.epreuve));
        } else {
            this.subscribeToSaveResponse(this.epreuveService.create(this.epreuve));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEpreuve>>) {
        result.subscribe((res: HttpResponse<IEpreuve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeEpreuveById(index: number, item: ITypeEpreuve) {
        return item.id;
    }
}
