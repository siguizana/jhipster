import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';
import { NoteMembreCritereService } from './note-membre-critere.service';
import { IMembreJury } from 'app/shared/model/membre-jury.model';
import { MembreJuryService } from 'app/entities/membre-jury';
import { ICritereChoixMembreJury } from 'app/shared/model/critere-choix-membre-jury.model';
import { CritereChoixMembreJuryService } from 'app/entities/critere-choix-membre-jury';

@Component({
    selector: 'jhi-note-membre-critere-update',
    templateUrl: './note-membre-critere-update.component.html'
})
export class NoteMembreCritereUpdateComponent implements OnInit {
    noteMembreCritere: INoteMembreCritere;
    isSaving: boolean;

    membrejuries: IMembreJury[];

    criterechoixmembrejuries: ICritereChoixMembreJury[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected noteMembreCritereService: NoteMembreCritereService,
        protected membreJuryService: MembreJuryService,
        protected critereChoixMembreJuryService: CritereChoixMembreJuryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ noteMembreCritere }) => {
            this.noteMembreCritere = noteMembreCritere;
        });
        this.membreJuryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMembreJury[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMembreJury[]>) => response.body)
            )
            .subscribe((res: IMembreJury[]) => (this.membrejuries = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.noteMembreCritere.id !== undefined) {
            this.subscribeToSaveResponse(this.noteMembreCritereService.update(this.noteMembreCritere));
        } else {
            this.subscribeToSaveResponse(this.noteMembreCritereService.create(this.noteMembreCritere));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INoteMembreCritere>>) {
        result.subscribe((res: HttpResponse<INoteMembreCritere>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMembreJuryById(index: number, item: IMembreJury) {
        return item.id;
    }

    trackCritereChoixMembreJuryById(index: number, item: ICritereChoixMembreJury) {
        return item.id;
    }
}
