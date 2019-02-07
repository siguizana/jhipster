import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from './candidat.service';

@Component({
    selector: 'jhi-candidat-update',
    templateUrl: './candidat-update.component.html'
})
export class CandidatUpdateComponent implements OnInit {
    candidat: ICandidat;
    isSaving: boolean;
    dateNaissanceDp: any;
    dateEnregistrementCandidatDp: any;
    dateDebutCariereCandidatDp: any;

    constructor(protected candidatService: CandidatService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ candidat }) => {
            this.candidat = candidat;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.candidat.id !== undefined) {
            this.subscribeToSaveResponse(this.candidatService.update(this.candidat));
        } else {
            this.subscribeToSaveResponse(this.candidatService.create(this.candidat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidat>>) {
        result.subscribe((res: HttpResponse<ICandidat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
