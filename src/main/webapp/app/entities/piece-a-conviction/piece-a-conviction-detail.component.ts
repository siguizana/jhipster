import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPieceAConviction } from 'app/shared/model/piece-a-conviction.model';

@Component({
    selector: 'jhi-piece-a-conviction-detail',
    templateUrl: './piece-a-conviction-detail.component.html'
})
export class PieceAConvictionDetailComponent implements OnInit {
    pieceAConviction: IPieceAConviction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pieceAConviction }) => {
            this.pieceAConviction = pieceAConviction;
        });
    }

    previousState() {
        window.history.back();
    }
}
