import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';
import { OptionConcoursRattacheService } from './option-concours-rattache.service';
import { IConcoursRattache } from 'app/shared/model/concours-rattache.model';
import { ConcoursRattacheService } from 'app/entities/concours-rattache';

@Component({
    selector: 'jhi-option-concours-rattache-update',
    templateUrl: './option-concours-rattache-update.component.html'
})
export class OptionConcoursRattacheUpdateComponent implements OnInit {
    optionConcoursRattache: IOptionConcoursRattache;
    isSaving: boolean;

    concoursrattaches: IConcoursRattache[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected optionConcoursRattacheService: OptionConcoursRattacheService,
        protected concoursRattacheService: ConcoursRattacheService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ optionConcoursRattache }) => {
            this.optionConcoursRattache = optionConcoursRattache;
        });
        this.concoursRattacheService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IConcoursRattache[]>) => mayBeOk.ok),
                map((response: HttpResponse<IConcoursRattache[]>) => response.body)
            )
            .subscribe((res: IConcoursRattache[]) => (this.concoursrattaches = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.optionConcoursRattache.id !== undefined) {
            this.subscribeToSaveResponse(this.optionConcoursRattacheService.update(this.optionConcoursRattache));
        } else {
            this.subscribeToSaveResponse(this.optionConcoursRattacheService.create(this.optionConcoursRattache));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionConcoursRattache>>) {
        result.subscribe(
            (res: HttpResponse<IOptionConcoursRattache>) => this.onSaveSuccess(),
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackConcoursRattacheById(index: number, item: IConcoursRattache) {
        return item.id;
    }
}
