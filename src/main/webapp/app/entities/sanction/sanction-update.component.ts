import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISanction } from 'app/shared/model/sanction.model';
import { SanctionService } from './sanction.service';
import { IFraude } from 'app/shared/model/fraude.model';
import { FraudeService } from 'app/entities/fraude';

@Component({
    selector: 'jhi-sanction-update',
    templateUrl: './sanction-update.component.html'
})
export class SanctionUpdateComponent implements OnInit {
    sanction: ISanction;
    isSaving: boolean;

    fraudes: IFraude[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sanctionService: SanctionService,
        protected fraudeService: FraudeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sanction }) => {
            this.sanction = sanction;
        });
        this.fraudeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFraude[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFraude[]>) => response.body)
            )
            .subscribe((res: IFraude[]) => (this.fraudes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sanction.id !== undefined) {
            this.subscribeToSaveResponse(this.sanctionService.update(this.sanction));
        } else {
            this.subscribeToSaveResponse(this.sanctionService.create(this.sanction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISanction>>) {
        result.subscribe((res: HttpResponse<ISanction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFraudeById(index: number, item: IFraude) {
        return item.id;
    }
}
