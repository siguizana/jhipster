import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMention } from 'app/shared/model/mention.model';
import { MentionService } from './mention.service';

@Component({
    selector: 'jhi-mention-update',
    templateUrl: './mention-update.component.html'
})
export class MentionUpdateComponent implements OnInit {
    mention: IMention;
    isSaving: boolean;

    constructor(protected mentionService: MentionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mention }) => {
            this.mention = mention;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mention.id !== undefined) {
            this.subscribeToSaveResponse(this.mentionService.update(this.mention));
        } else {
            this.subscribeToSaveResponse(this.mentionService.create(this.mention));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMention>>) {
        result.subscribe((res: HttpResponse<IMention>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
