import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPieceAConviction } from 'app/shared/model/piece-a-conviction.model';
import { PieceAConvictionService } from './piece-a-conviction.service';
import { IFraude } from 'app/shared/model/fraude.model';
import { FraudeService } from 'app/entities/fraude';

@Component({
    selector: 'jhi-piece-a-conviction-update',
    templateUrl: './piece-a-conviction-update.component.html'
})
export class PieceAConvictionUpdateComponent implements OnInit {
    pieceAConviction: IPieceAConviction;
    isSaving: boolean;

    fraudes: IFraude[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pieceAConvictionService: PieceAConvictionService,
        protected fraudeService: FraudeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pieceAConviction }) => {
            this.pieceAConviction = pieceAConviction;
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
        if (this.pieceAConviction.id !== undefined) {
            this.subscribeToSaveResponse(this.pieceAConvictionService.update(this.pieceAConviction));
        } else {
            this.subscribeToSaveResponse(this.pieceAConvictionService.create(this.pieceAConviction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPieceAConviction>>) {
        result.subscribe((res: HttpResponse<IPieceAConviction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
