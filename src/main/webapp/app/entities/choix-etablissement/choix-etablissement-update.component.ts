import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IChoixEtablissement } from 'app/shared/model/choix-etablissement.model';
import { ChoixEtablissementService } from './choix-etablissement.service';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement';
import { ISpecialiteOption } from 'app/shared/model/specialite-option.model';
import { SpecialiteOptionService } from 'app/entities/specialite-option';

@Component({
    selector: 'jhi-choix-etablissement-update',
    templateUrl: './choix-etablissement-update.component.html'
})
export class ChoixEtablissementUpdateComponent implements OnInit {
    choixEtablissement: IChoixEtablissement;
    isSaving: boolean;

    inscriptions: IInscription[];

    etablissements: IEtablissement[];

    specialiteoptions: ISpecialiteOption[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected choixEtablissementService: ChoixEtablissementService,
        protected inscriptionService: InscriptionService,
        protected etablissementService: EtablissementService,
        protected specialiteOptionService: SpecialiteOptionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ choixEtablissement }) => {
            this.choixEtablissement = choixEtablissement;
        });
        this.inscriptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInscription[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInscription[]>) => response.body)
            )
            .subscribe((res: IInscription[]) => (this.inscriptions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.etablissementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEtablissement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEtablissement[]>) => response.body)
            )
            .subscribe((res: IEtablissement[]) => (this.etablissements = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.specialiteOptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISpecialiteOption[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISpecialiteOption[]>) => response.body)
            )
            .subscribe((res: ISpecialiteOption[]) => (this.specialiteoptions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.choixEtablissement.id !== undefined) {
            this.subscribeToSaveResponse(this.choixEtablissementService.update(this.choixEtablissement));
        } else {
            this.subscribeToSaveResponse(this.choixEtablissementService.create(this.choixEtablissement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IChoixEtablissement>>) {
        result.subscribe((res: HttpResponse<IChoixEtablissement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEtablissementById(index: number, item: IEtablissement) {
        return item.id;
    }

    trackSpecialiteOptionById(index: number, item: ISpecialiteOption) {
        return item.id;
    }
}
