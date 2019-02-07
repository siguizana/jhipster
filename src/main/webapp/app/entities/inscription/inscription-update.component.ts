import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from './inscription.service';
import { IVillageSecteur } from 'app/shared/model/village-secteur.model';
import { VillageSecteurService } from 'app/entities/village-secteur';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';
import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';
import { OptionConcoursRattacheService } from 'app/entities/option-concours-rattache';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat';
import { ITypeExamen } from 'app/shared/model/type-examen.model';
import { TypeExamenService } from 'app/entities/type-examen';
import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { CentreCompositioJuryService } from 'app/entities/centre-compositio-jury';
import { ISalleComposition } from 'app/shared/model/salle-composition.model';
import { SalleCompositionService } from 'app/entities/salle-composition';
import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from 'app/entities/jury';

@Component({
    selector: 'jhi-inscription-update',
    templateUrl: './inscription-update.component.html'
})
export class InscriptionUpdateComponent implements OnInit {
    inscription: IInscription;
    isSaving: boolean;

    villagesecteurs: IVillageSecteur[];

    etablissements: IEtablissement[];

    sessions: ISession[];

    optionconcoursrattaches: IOptionConcoursRattache[];

    candidats: ICandidat[];

    typeexamen: ITypeExamen[];

    centrecompositiojuries: ICentreCompositioJury[];

    sallecompositions: ISalleComposition[];

    juries: IJury[];
    dateInscriptionDp: any;
    dateDernierePromotionDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected inscriptionService: InscriptionService,
        protected villageSecteurService: VillageSecteurService,
        protected etablissementService: EtablissementService,
        protected sessionService: SessionService,
        protected optionConcoursRattacheService: OptionConcoursRattacheService,
        protected candidatService: CandidatService,
        protected typeExamenService: TypeExamenService,
        protected centreCompositioJuryService: CentreCompositioJuryService,
        protected salleCompositionService: SalleCompositionService,
        protected juryService: JuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ inscription }) => {
            this.inscription = inscription;
        });
        this.villageSecteurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVillageSecteur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVillageSecteur[]>) => response.body)
            )
            .subscribe((res: IVillageSecteur[]) => (this.villagesecteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.etablissementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEtablissement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEtablissement[]>) => response.body)
            )
            .subscribe((res: IEtablissement[]) => (this.etablissements = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.sessionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISession[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISession[]>) => response.body)
            )
            .subscribe((res: ISession[]) => (this.sessions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.optionConcoursRattacheService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOptionConcoursRattache[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOptionConcoursRattache[]>) => response.body)
            )
            .subscribe(
                (res: IOptionConcoursRattache[]) => (this.optionconcoursrattaches = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.candidatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICandidat[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICandidat[]>) => response.body)
            )
            .subscribe((res: ICandidat[]) => (this.candidats = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExamen[]>) => response.body)
            )
            .subscribe((res: ITypeExamen[]) => (this.typeexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.centreCompositioJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICentreCompositioJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICentreCompositioJury[]>) => response.body)
            )
            .subscribe(
                (res: ICentreCompositioJury[]) => (this.centrecompositiojuries = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.salleCompositionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISalleComposition[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISalleComposition[]>) => response.body)
            )
            .subscribe((res: ISalleComposition[]) => (this.sallecompositions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.juryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IJury[]>) => response.body)
            )
            .subscribe((res: IJury[]) => (this.juries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.inscription.id !== undefined) {
            this.subscribeToSaveResponse(this.inscriptionService.update(this.inscription));
        } else {
            this.subscribeToSaveResponse(this.inscriptionService.create(this.inscription));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscription>>) {
        result.subscribe((res: HttpResponse<IInscription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVillageSecteurById(index: number, item: IVillageSecteur) {
        return item.id;
    }

    trackEtablissementById(index: number, item: IEtablissement) {
        return item.id;
    }

    trackSessionById(index: number, item: ISession) {
        return item.id;
    }

    trackOptionConcoursRattacheById(index: number, item: IOptionConcoursRattache) {
        return item.id;
    }

    trackCandidatById(index: number, item: ICandidat) {
        return item.id;
    }

    trackTypeExamenById(index: number, item: ITypeExamen) {
        return item.id;
    }

    trackCentreCompositioJuryById(index: number, item: ICentreCompositioJury) {
        return item.id;
    }

    trackSalleCompositionById(index: number, item: ISalleComposition) {
        return item.id;
    }

    trackJuryById(index: number, item: IJury) {
        return item.id;
    }
}
