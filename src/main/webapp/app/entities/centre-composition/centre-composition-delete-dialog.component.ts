import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICentreComposition } from 'app/shared/model/centre-composition.model';
import { CentreCompositionService } from './centre-composition.service';

@Component({
    selector: 'jhi-centre-composition-delete-dialog',
    templateUrl: './centre-composition-delete-dialog.component.html'
})
export class CentreCompositionDeleteDialogComponent {
    centreComposition: ICentreComposition;

    constructor(
        protected centreCompositionService: CentreCompositionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.centreCompositionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'centreCompositionListModification',
                content: 'Deleted an centreComposition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-centre-composition-delete-popup',
    template: ''
})
export class CentreCompositionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ centreComposition }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CentreCompositionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.centreComposition = centreComposition;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/centre-composition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/centre-composition', { outlets: { popup: null } }]);
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
