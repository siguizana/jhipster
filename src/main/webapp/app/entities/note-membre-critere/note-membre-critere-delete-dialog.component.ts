import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INoteMembreCritere } from 'app/shared/model/note-membre-critere.model';
import { NoteMembreCritereService } from './note-membre-critere.service';

@Component({
    selector: 'jhi-note-membre-critere-delete-dialog',
    templateUrl: './note-membre-critere-delete-dialog.component.html'
})
export class NoteMembreCritereDeleteDialogComponent {
    noteMembreCritere: INoteMembreCritere;

    constructor(
        protected noteMembreCritereService: NoteMembreCritereService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.noteMembreCritereService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'noteMembreCritereListModification',
                content: 'Deleted an noteMembreCritere'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-note-membre-critere-delete-popup',
    template: ''
})
export class NoteMembreCritereDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ noteMembreCritere }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NoteMembreCritereDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.noteMembreCritere = noteMembreCritere;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/note-membre-critere', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/note-membre-critere', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
