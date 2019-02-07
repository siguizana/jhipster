import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';
import { DeroulementScolariteService } from './deroulement-scolarite.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat';

@Component({
    selector: 'jhi-deroulement-scolarite-update',
    templateUrl: './deroulement-scolarite-update.component.html'
})
export class DeroulementScolariteUpdateComponent implements OnInit {
    deroulementScolarite: IDeroulementScolarite;
    isSaving: boolean;

    candidats: ICandidat[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected deroulementScolariteService: DeroulementScolariteService,
        protected candidatService: CandidatService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deroulementScolarite }) => {
            this.deroulementScolarite = deroulementScolarite;
        });
        this.candidatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidat[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidat[]>) => response.body)
            )
            .subscribe((res: ICandidat[]) => (this.candidats = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deroulementScolarite.id !== undefined) {
            this.subscribeToSaveResponse(this.deroulementScolariteService.update(this.deroulementScolarite));
        } else {
            this.subscribeToSaveResponse(this.deroulementScolariteService.create(this.deroulementScolarite));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeroulementScolarite>>) {
        result.subscribe(
            (res: HttpResponse<IDeroulementScolarite>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackCandidatById(index: number, item: ICandidat) {
        return item.id;
    }
}
