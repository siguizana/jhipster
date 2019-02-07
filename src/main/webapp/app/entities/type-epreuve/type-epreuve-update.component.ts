import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeEpreuve } from 'app/shared/model/type-epreuve.model';
import { TypeEpreuveService } from './type-epreuve.service';

@Component({
    selector: 'jhi-type-epreuve-update',
    templateUrl: './type-epreuve-update.component.html'
})
export class TypeEpreuveUpdateComponent implements OnInit {
    typeEpreuve: ITypeEpreuve;
    isSaving: boolean;

    constructor(protected typeEpreuveService: TypeEpreuveService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeEpreuve }) => {
            this.typeEpreuve = typeEpreuve;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeEpreuve.id !== undefined) {
            this.subscribeToSaveResponse(this.typeEpreuveService.update(this.typeEpreuve));
        } else {
            this.subscribeToSaveResponse(this.typeEpreuveService.create(this.typeEpreuve));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeEpreuve>>) {
        result.subscribe((res: HttpResponse<ITypeEpreuve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
