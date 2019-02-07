import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVillageSecteur } from 'app/shared/model/village-secteur.model';
import { VillageSecteurService } from './village-secteur.service';
import { ICommune } from 'app/shared/model/commune.model';
import { CommuneService } from 'app/entities/commune';

@Component({
    selector: 'jhi-village-secteur-update',
    templateUrl: './village-secteur-update.component.html'
})
export class VillageSecteurUpdateComponent implements OnInit {
    villageSecteur: IVillageSecteur;
    isSaving: boolean;

    communes: ICommune[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected villageSecteurService: VillageSecteurService,
        protected communeService: CommuneService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ villageSecteur }) => {
            this.villageSecteur = villageSecteur;
        });
        this.communeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommune[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommune[]>) => response.body)
            )
            .subscribe((res: ICommune[]) => (this.communes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.villageSecteur.id !== undefined) {
            this.subscribeToSaveResponse(this.villageSecteurService.update(this.villageSecteur));
        } else {
            this.subscribeToSaveResponse(this.villageSecteurService.create(this.villageSecteur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVillageSecteur>>) {
        result.subscribe((res: HttpResponse<IVillageSecteur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommuneById(index: number, item: ICommune) {
        return item.id;
    }
}
