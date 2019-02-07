import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';

@Component({
    selector: 'jhi-note-membre-critere-detail',
    templateUrl: './note-membre-critere-detail.component.html'
})
export class NoteMembreCritereDetailComponent implements OnInit {
    noteMembreCritere: INoteMembreCritere;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ noteMembreCritere }) => {
            this.noteMembreCritere = noteMembreCritere;
        });
    }

    previousState() {
        window.history.back();
    }
}
