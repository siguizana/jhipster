import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeCentreComposition } from 'app/shared/model/type-centre-composition.model';
import { TypeCentreCompositionService } from './type-centre-composition.service';

@Component({
    selector: 'jhi-type-centre-composition-update',
    templateUrl: './type-centre-composition-update.component.html'
})
export class TypeCentreCompositionUpdateComponent implements OnInit {
    typeCentreComposition: ITypeCentreComposition;
    isSaving: boolean;

    constructor(protected typeCentreCompositionService: TypeCentreCompositionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeCentreComposition }) => {
            this.typeCentreComposition = typeCentreComposition;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeCentreComposition.id !== undefined) {
            this.subscribeToSaveResponse(this.typeCentreCompositionService.update(this.typeCentreComposition));
        } else {
            this.subscribeToSaveResponse(this.typeCentreCompositionService.create(this.typeCentreComposition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCentreComposition>>) {
        result.subscribe(
            (res: HttpResponse<ITypeCentreComposition>) => this.onSaveSuccess(),
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
}
