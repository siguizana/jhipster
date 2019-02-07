import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeCritere } from 'app/shared/model/type-critere.model';
import { TypeCritereService } from './type-critere.service';

@Component({
    selector: 'jhi-type-critere-update',
    templateUrl: './type-critere-update.component.html'
})
export class TypeCritereUpdateComponent implements OnInit {
    typeCritere: ITypeCritere;
    isSaving: boolean;

    constructor(protected typeCritereService: TypeCritereService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeCritere }) => {
            this.typeCritere = typeCritere;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeCritere.id !== undefined) {
            this.subscribeToSaveResponse(this.typeCritereService.update(this.typeCritere));
        } else {
            this.subscribeToSaveResponse(this.typeCritereService.create(this.typeCritere));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCritere>>) {
        result.subscribe((res: HttpResponse<ITypeCritere>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
