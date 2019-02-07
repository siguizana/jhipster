import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeEtablissement } from 'app/shared/model/type-etablissement.model';
import { TypeEtablissementService } from './type-etablissement.service';

@Component({
    selector: 'jhi-type-etablissement-update',
    templateUrl: './type-etablissement-update.component.html'
})
export class TypeEtablissementUpdateComponent implements OnInit {
    typeEtablissement: ITypeEtablissement;
    isSaving: boolean;

    constructor(protected typeEtablissementService: TypeEtablissementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeEtablissement }) => {
            this.typeEtablissement = typeEtablissement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeEtablissement.id !== undefined) {
            this.subscribeToSaveResponse(this.typeEtablissementService.update(this.typeEtablissement));
        } else {
            this.subscribeToSaveResponse(this.typeEtablissementService.create(this.typeEtablissement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeEtablissement>>) {
        result.subscribe((res: HttpResponse<ITypeEtablissement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
