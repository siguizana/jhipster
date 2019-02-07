import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';
import { CritereChoixMembreJuryService } from './critere-choix-membre-jury.service';
import { ITypeMembreJury } from 'app/shared/model/type-membre-jury.model';
import { TypeMembreJuryService } from 'app/entities/type-membre-jury';

@Component({
    selector: 'jhi-critere-choix-membre-jury-update',
    templateUrl: './critere-choix-membre-jury-update.component.html'
})
export class CritereChoixMembreJuryUpdateComponent implements OnInit {
    critereChoixMembreJury: ICritereChoixMembreJury;
    isSaving: boolean;

    typemembrejuries: ITypeMembreJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected critereChoixMembreJuryService: CritereChoixMembreJuryService,
        protected typeMembreJuryService: TypeMembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ critereChoixMembreJury }) => {
            this.critereChoixMembreJury = critereChoixMembreJury;
        });
        this.typeMembreJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeMembreJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeMembreJury[]>) => response.body)
            )
            .subscribe((res: ITypeMembreJury[]) => (this.typemembrejuries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.critereChoixMembreJury.id !== undefined) {
            this.subscribeToSaveResponse(this.critereChoixMembreJuryService.update(this.critereChoixMembreJury));
        } else {
            this.subscribeToSaveResponse(this.critereChoixMembreJuryService.create(this.critereChoixMembreJury));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICritereChoixMembreJury>>) {
        result.subscribe(
            (res: HttpResponse<ICritereChoixMembreJury>) => this.onSaveSuccess(),
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

    trackTypeMembreJuryById(index: number, item: ITypeMembreJury) {
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
