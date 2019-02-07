import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEnseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from './enseignant.service';

@Component({
    selector: 'jhi-enseignant-update',
    templateUrl: './enseignant-update.component.html'
})
export class EnseignantUpdateComponent implements OnInit {
    enseignant: IEnseignant;
    isSaving: boolean;

    constructor(protected enseignantService: EnseignantService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ enseignant }) => {
            this.enseignant = enseignant;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.enseignant.id !== undefined) {
            this.subscribeToSaveResponse(this.enseignantService.update(this.enseignant));
        } else {
            this.subscribeToSaveResponse(this.enseignantService.create(this.enseignant));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnseignant>>) {
        result.subscribe((res: HttpResponse<IEnseignant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
