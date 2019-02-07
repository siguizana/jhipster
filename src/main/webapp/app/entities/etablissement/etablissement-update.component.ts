import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { EtablissementService } from './etablissement.service';
import { ICeb } from 'app/shared/model/ceb.model';
import { CebService } from 'app/entities/ceb';
import { ITypeEtablissement } from 'app/shared/model/type-etablissement.model';
import { TypeEtablissementService } from 'app/entities/type-etablissement';
import { IVillageSecteur } from 'app/shared/model/village-secteur.model';
import { VillageSecteurService } from 'app/entities/village-secteur';
import { ICentreComposition } from 'app/shared/model/centre-composition.model';
import { CentreCompositionService } from 'app/entities/centre-composition';

@Component({
    selector: 'jhi-etablissement-update',
    templateUrl: './etablissement-update.component.html'
})
export class EtablissementUpdateComponent implements OnInit {
    etablissement: IEtablissement;
    isSaving: boolean;

    cebs: ICeb[];

    typeetablissements: ITypeEtablissement[];

    villagesecteurs: IVillageSecteur[];

    centrecompositions: ICentreComposition[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected etablissementService: EtablissementService,
        protected cebService: CebService,
        protected typeEtablissementService: TypeEtablissementService,
        protected villageSecteurService: VillageSecteurService,
        protected centreCompositionService: CentreCompositionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ etablissement }) => {
            this.etablissement = etablissement;
        });
        this.cebService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICeb[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICeb[]>) => response.body)
            )
            .subscribe((res: ICeb[]) => (this.cebs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeEtablissementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeEtablissement[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeEtablissement[]>) => response.body)
            )
            .subscribe(
                (res: ITypeEtablissement[]) => (this.typeetablissements = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.villageSecteurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVillageSecteur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVillageSecteur[]>) => response.body)
            )
            .subscribe((res: IVillageSecteur[]) => (this.villagesecteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.centreCompositionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICentreComposition[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICentreComposition[]>) => response.body)
            )
            .subscribe(
                (res: ICentreComposition[]) => (this.centrecompositions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.etablissement.id !== undefined) {
            this.subscribeToSaveResponse(this.etablissementService.update(this.etablissement));
        } else {
            this.subscribeToSaveResponse(this.etablissementService.create(this.etablissement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablissement>>) {
        result.subscribe((res: HttpResponse<IEtablissement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCebById(index: number, item: ICeb) {
        return item.id;
    }

    trackTypeEtablissementById(index: number, item: ITypeEtablissement) {
        return item.id;
    }

    trackVillageSecteurById(index: number, item: IVillageSecteur) {
        return item.id;
    }

    trackCentreCompositionById(index: number, item: ICentreComposition) {
        return item.id;
    }
}
