import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResultat } from 'app/shared/model/resultat.model';
import { ResultatService } from './resultat.service';
import { IEtapeExamen } from 'app/shared/model/etape-examen.model';
import { EtapeExamenService } from 'app/entities/etape-examen';
import { IMention } from 'app/shared/model/mention.model';
import { MentionService } from 'app/entities/mention';
import { ITypeDecision } from 'app/shared/model/type-decision.model';
import { TypeDecisionService } from 'app/entities/type-decision';
import { IOptionConcoursRattache } from 'app/shared/model/option-concours-rattache.model';
import { OptionConcoursRattacheService } from 'app/entities/option-concours-rattache';
import { IInscription } from 'app/shared/model/inscription.model';
import { InscriptionService } from 'app/entities/inscription';

@Component({
    selector: 'jhi-resultat-update',
    templateUrl: './resultat-update.component.html'
})
export class ResultatUpdateComponent implements OnInit {
    resultat: IResultat;
    isSaving: boolean;

    etapeexamen: IEtapeExamen[];

    mentions: IMention[];

    typedecisions: ITypeDecision[];

    optionconcoursrattaches: IOptionConcoursRattache[];

    inscriptions: IInscription[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected resultatService: ResultatService,
        protected etapeExamenService: EtapeExamenService,
        protected mentionService: MentionService,
        protected typeDecisionService: TypeDecisionService,
        protected optionConcoursRattacheService: OptionConcoursRattacheService,
        protected inscriptionService: InscriptionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resultat }) => {
            this.resultat = resultat;
        });
        this.etapeExamenService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEtapeExamen[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEtapeExamen[]>) => response.body)
            )
            .subscribe((res: IEtapeExamen[]) => (this.etapeexamen = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.mentionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMention[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMention[]>) => response.body)
            )
            .subscribe((res: IMention[]) => (this.mentions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeDecisionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeDecision[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeDecision[]>) => response.body)
            )
            .subscribe((res: ITypeDecision[]) => (this.typedecisions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.inscriptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInscription[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInscription[]>) => response.body)
            )
            .subscribe((res: IInscription[]) => (this.inscriptions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resultat.id !== undefined) {
            this.subscribeToSaveResponse(this.resultatService.update(this.resultat));
        } else {
            this.subscribeToSaveResponse(this.resultatService.create(this.resultat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultat>>) {
        result.subscribe((res: HttpResponse<IResultat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEtapeExamenById(index: number, item: IEtapeExamen) {
        return item.id;
    }

    trackMentionById(index: number, item: IMention) {
        return item.id;
    }

    trackTypeDecisionById(index: number, item: ITypeDecision) {
        return item.id;
    }

    trackOptionConcoursRattacheById(index: number, item: IOptionConcoursRattache) {
        return item.id;
    }

    trackInscriptionById(index: number, item: IInscription) {
        return item.id;
    }
}
