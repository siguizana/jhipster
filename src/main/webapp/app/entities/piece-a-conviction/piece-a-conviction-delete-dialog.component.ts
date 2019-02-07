import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPieceAConviction } from 'app/shared/model/piece-a-conviction.model';
import { PieceAConvictionService } from './piece-a-conviction.service';

@Component({
    selector: 'jhi-piece-a-conviction-delete-dialog',
    templateUrl: './piece-a-conviction-delete-dialog.component.html'
})
export class PieceAConvictionDeleteDialogComponent {
    pieceAConviction: IPieceAConviction;

    constructor(
        protected pieceAConvictionService: PieceAConvictionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pieceAConvictionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pieceAConvictionListModification',
                content: 'Deleted an pieceAConviction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-piece-a-conviction-delete-popup',
    template: ''
})
export class PieceAConvictionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pieceAConviction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PieceAConvictionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pieceAConviction = pieceAConviction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/piece-a-conviction', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/piece-a-conviction', { outlets: { popup: null } }]);
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
