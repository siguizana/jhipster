import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeFraude } from 'app/shared/model/type-fraude.model';
import { TypeFraudeService } from './type-fraude.service';

@Component({
    selector: 'jhi-type-fraude-update',
    templateUrl: './type-fraude-update.component.html'
})
export class TypeFraudeUpdateComponent implements OnInit {
    typeFraude: ITypeFraude;
    isSaving: boolean;

    constructor(protected typeFraudeService: TypeFraudeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeFraude }) => {
            this.typeFraude = typeFraude;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeFraude.id !== undefined) {
            this.subscribeToSaveResponse(this.typeFraudeService.update(this.typeFraude));
        } else {
            this.subscribeToSaveResponse(this.typeFraudeService.create(this.typeFraude));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeFraude>>) {
        result.subscribe((res: HttpResponse<ITypeFraude>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
