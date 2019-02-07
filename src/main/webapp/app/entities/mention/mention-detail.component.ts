import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMention } from 'app/shared/model/mention.model';

@Component({
    selector: 'jhi-mention-detail',
    templateUrl: './mention-detail.component.html'
})
export class MentionDetailComponent implements OnInit {
    mention: IMention;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mention }) => {
            this.mention = mention;
        });
    }

    previousState() {
        window.history.back();
    }
}
