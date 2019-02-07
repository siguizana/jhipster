import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';
import { TypeMembreJuryService } from './type-membre-jury.service';
import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';
import { CritereChoixMembreJuryService } from 'app/entities/critere-choix-membre-jury';

@Component({
    selector: 'jhi-type-membre-jury-update',
    templateUrl: './type-membre-jury-update.component.html'
})
export class TypeMembreJuryUpdateComponent implements OnInit {
    typeMembreJury: ITypeMembreJury;
    isSaving: boolean;

    criterechoixmembrejuries: ICritereChoixMembreJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected typeMembreJuryService: TypeMembreJuryService,
        protected critereChoixMembreJuryService: CritereChoixMembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeMembreJury }) => {
            this.typeMembreJury = typeMembreJury;
        });
        this.critereChoixMembreJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICritereChoixMembreJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICritereChoixMembreJury[]>) => response.body)
            )
            .subscribe(
                (res: ICritereChoixMembreJury[]) => (this.criterechoixmembrejuries = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeMembreJury.id !== undefined) {
            this.subscribeToSaveResponse(this.typeMembreJuryService.update(this.typeMembreJury));
        } else {
            this.subscribeToSaveResponse(this.typeMembreJuryService.create(this.typeMembreJury));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeMembreJury>>) {
        result.subscribe((res: HttpResponse<ITypeMembreJury>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCritereChoixMembreJuryById(index: number, item: ICritereChoixMembreJury) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
