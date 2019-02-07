import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeDiplome } from 'app/shared/model/type-diplome.model';
import { TypeDiplomeService } from './type-diplome.service';

@Component({
    selector: 'jhi-type-diplome-update',
    templateUrl: './type-diplome-update.component.html'
})
export class TypeDiplomeUpdateComponent implements OnInit {
    typeDiplome: ITypeDiplome;
    isSaving: boolean;

    constructor(protected typeDiplomeService: TypeDiplomeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeDiplome }) => {
            this.typeDiplome = typeDiplome;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeDiplome.id !== undefined) {
            this.subscribeToSaveResponse(this.typeDiplomeService.update(this.typeDiplome));
        } else {
            this.subscribeToSaveResponse(this.typeDiplomeService.create(this.typeDiplome));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeDiplome>>) {
        result.subscribe((res: HttpResponse<ITypeDiplome>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
