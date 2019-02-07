import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';

@Component({
    selector: 'jhi-jury-update',
    templateUrl: './jury-update.component.html'
})
export class JuryUpdateComponent implements OnInit {
    jury: IJury;
    isSaving: boolean;

    constructor(protected juryService: JuryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jury }) => {
            this.jury = jury;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.jury.id !== undefined) {
            this.subscribeToSaveResponse(this.juryService.update(this.jury));
        } else {
            this.subscribeToSaveResponse(this.juryService.create(this.jury));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJury>>) {
        result.subscribe((res: HttpResponse<IJury>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
