import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICeb } from 'app/shared/model/ceb.model';
import { CebService } from './ceb.service';
import { ICommune } from 'app/shared/model/commune.model';
import { CommuneService } from 'app/entities/commune';

@Component({
    selector: 'jhi-ceb-update',
    templateUrl: './ceb-update.component.html'
})
export class CebUpdateComponent implements OnInit {
    ceb: ICeb;
    isSaving: boolean;

    communes: ICommune[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected cebService: CebService,
        protected communeService: CommuneService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ceb }) => {
            this.ceb = ceb;
        });
        this.communeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommune[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommune[]>) => response.body)
            )
            .subscribe((res: ICommune[]) => (this.communes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ceb.id !== undefined) {
            this.subscribeToSaveResponse(this.cebService.update(this.ceb));
        } else {
            this.subscribeToSaveResponse(this.cebService.create(this.ceb));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICeb>>) {
        result.subscribe((res: HttpResponse<ICeb>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommuneById(index: number, item: ICommune) {
        return item.id;
    }
}
