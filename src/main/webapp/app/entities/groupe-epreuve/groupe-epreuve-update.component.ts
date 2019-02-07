import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IGroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';
import { GroupeEpreuveService } from './groupe-epreuve.service';

@Component({
    selector: 'jhi-groupe-epreuve-update',
    templateUrl: './groupe-epreuve-update.component.html'
})
export class GroupeEpreuveUpdateComponent implements OnInit {
    groupeEpreuve: IGroupeEpreuve;
    isSaving: boolean;

    constructor(protected groupeEpreuveService: GroupeEpreuveService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupeEpreuve }) => {
            this.groupeEpreuve = groupeEpreuve;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groupeEpreuve.id !== undefined) {
            this.subscribeToSaveResponse(this.groupeEpreuveService.update(this.groupeEpreuve));
        } else {
            this.subscribeToSaveResponse(this.groupeEpreuveService.create(this.groupeEpreuve));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupeEpreuve>>) {
        result.subscribe((res: HttpResponse<IGroupeEpreuve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
