import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IHandicape } from 'app/shared/model/handicape.model';
import { HandicapeService } from './handicape.service';

@Component({
    selector: 'jhi-handicape-update',
    templateUrl: './handicape-update.component.html'
})
export class HandicapeUpdateComponent implements OnInit {
    handicape: IHandicape;
    isSaving: boolean;

    constructor(protected handicapeService: HandicapeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ handicape }) => {
            this.handicape = handicape;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.handicape.id !== undefined) {
            this.subscribeToSaveResponse(this.handicapeService.update(this.handicape));
        } else {
            this.subscribeToSaveResponse(this.handicapeService.create(this.handicape));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHandicape>>) {
        result.subscribe((res: HttpResponse<IHandicape>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
