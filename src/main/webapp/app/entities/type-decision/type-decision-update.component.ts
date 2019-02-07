import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeDecision } from 'app/shared/model/type-decision.model';
import { TypeDecisionService } from './type-decision.service';

@Component({
    selector: 'jhi-type-decision-update',
    templateUrl: './type-decision-update.component.html'
})
export class TypeDecisionUpdateComponent implements OnInit {
    typeDecision: ITypeDecision;
    isSaving: boolean;

    constructor(protected typeDecisionService: TypeDecisionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeDecision }) => {
            this.typeDecision = typeDecision;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeDecision.id !== undefined) {
            this.subscribeToSaveResponse(this.typeDecisionService.update(this.typeDecision));
        } else {
            this.subscribeToSaveResponse(this.typeDecisionService.create(this.typeDecision));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeDecision>>) {
        result.subscribe((res: HttpResponse<ITypeDecision>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
