import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from './type-examen.service';

@Component({
    selector: 'jhi-type-examen-update',
    templateUrl: './type-examen-update.component.html'
})
export class TypeExamenUpdateComponent implements OnInit {
    typeExamen: ITypeExamen;
    isSaving: boolean;

    constructor(protected typeExamenService: TypeExamenService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeExamen }) => {
            this.typeExamen = typeExamen;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeExamen.id !== undefined) {
            this.subscribeToSaveResponse(this.typeExamenService.update(this.typeExamen));
        } else {
            this.subscribeToSaveResponse(this.typeExamenService.create(this.typeExamen));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeExamen>>) {
        result.subscribe((res: HttpResponse<ITypeExamen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
