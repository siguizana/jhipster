import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEnseigne } from 'app/shared/model/enseigne.model';
import { EnseigneService } from './enseigne.service';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement';
import { IEnseignant } from 'app/shared/model/enseignant.model';
import { EnseignantService } from 'app/entities/enseignant';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session';

@Component({
    selector: 'jhi-enseigne-update',
    templateUrl: './enseigne-update.component.html'
})
export class EnseigneUpdateComponent implements OnInit {
    enseigne: IEnseigne;
    isSaving: boolean;

    etablissements: IEtablissement[];

    enseignants: IEnseignant[];

    sessions: ISession[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected enseigneService: EnseigneService,
        protected etablissementService: EtablissementService,
        protected enseignantService: EnseignantService,
        protected sessionService: SessionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ enseigne }) => {
            this.enseigne = enseigne;
        });
        this.etablissementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEtablissement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEtablissement[]>) => response.body)
            )
            .subscribe((res: IEtablissement[]) => (this.etablissements = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.enseignantService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEnseignant[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEnseignant[]>) => response.body)
            )
            .subscribe((res: IEnseignant[]) => (this.enseignants = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.sessionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISession[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISession[]>) => response.body)
            )
            .subscribe((res: ISession[]) => (this.sessions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.enseigne.id !== undefined) {
            this.subscribeToSaveResponse(this.enseigneService.update(this.enseigne));
        } else {
            this.subscribeToSaveResponse(this.enseigneService.create(this.enseigne));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnseigne>>) {
        result.subscribe((res: HttpResponse<IEnseigne>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEtablissementById(index: number, item: IEtablissement) {
        return item.id;
    }

    trackEnseignantById(index: number, item: IEnseignant) {
        return item.id;
    }

    trackSessionById(index: number, item: ISession) {
        return item.id;
    }
}
