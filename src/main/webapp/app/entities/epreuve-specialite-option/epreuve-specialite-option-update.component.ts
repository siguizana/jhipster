import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEpreuveSpecialiteOption } from 'app/shared/model/epreuve-specialite-option.model';
import { EpreuveSpecialiteOptionService } from './epreuve-specialite-option.service';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';
import { IEpreuve } from 'app/shared/model/epreuve.model';
import { EpreuveService } from 'app/entities/epreuve';
import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';
import { SpecialiteOptionService } from 'app/entities/specialite-option';
import { IGroupeEpreuve } from 'app/shared/model/groupe-epreuve.model';
import { GroupeEpreuveService } from 'app/entities/groupe-epreuve';
import { IDispense } from 'app/shared/model/dispense.model';
import { DispenseService } from 'app/entities/dispense';

@Component({
    selector: 'jhi-epreuve-specialite-option-update',
    templateUrl: './epreuve-specialite-option-update.component.html'
})
export class EpreuveSpecialiteOptionUpdateComponent implements OnInit {
    epreuveSpecialiteOption: IEpreuveSpecialiteOption;
    isSaving: boolean;

    inscriptions: IInscription[];

    epreuves: IEpreuve[];

    specialiteoptions: ISpecialiteOption[];

    groupeepreuves: IGroupeEpreuve[];

    dispenses: IDispense[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected epreuveSpecialiteOptionService: EpreuveSpecialiteOptionService,
        protected inscriptionService: InscriptionService,
        protected epreuveService: EpreuveService,
        protected specialiteOptionService: SpecialiteOptionService,
        protected groupeEpreuveService: GroupeEpreuveService,
        protected dispenseService: DispenseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ epreuveSpecialiteOption }) => {
            this.epreuveSpecialiteOption = epreuveSpecialiteOption;
        });
        this.inscriptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInscription[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInscription[]>) => response.body)
            )
            .subscribe((res: IInscription[]) => (this.inscriptions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.epreuveService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEpreuve[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEpreuve[]>) => response.body)
            )
            .subscribe((res: IEpreuve[]) => (this.epreuves = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.specialiteOptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISpecialiteOption[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISpecialiteOption[]>) => response.body)
            )
            .subscribe((res: ISpecialiteOption[]) => (this.specialiteoptions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.groupeEpreuveService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGroupeEpreuve[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGroupeEpreuve[]>) => response.body)
            )
            .subscribe((res: IGroupeEpreuve[]) => (this.groupeepreuves = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.dispenseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDispense[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDispense[]>) => response.body)
            )
            .subscribe((res: IDispense[]) => (this.dispenses = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.epreuveSpecialiteOption.id !== undefined) {
            this.subscribeToSaveResponse(this.epreuveSpecialiteOptionService.update(this.epreuveSpecialiteOption));
        } else {
            this.subscribeToSaveResponse(this.epreuveSpecialiteOptionService.create(this.epreuveSpecialiteOption));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEpreuveSpecialiteOption>>) {
        result.subscribe(
            (res: HttpResponse<IEpreuveSpecialiteOption>) => this.onSaveSuccess(),
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

    trackInscriptionById(index: number, item: IInscription) {
        return item.id;
    }

    trackEpreuveById(index: number, item: IEpreuve) {
        return item.id;
    }

    trackSpecialiteOptionById(index: number, item: ISpecialiteOption) {
        return item.id;
    }

    trackGroupeEpreuveById(index: number, item: IGroupeEpreuve) {
        return item.id;
    }

    trackDispenseById(index: number, item: IDispense) {
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
