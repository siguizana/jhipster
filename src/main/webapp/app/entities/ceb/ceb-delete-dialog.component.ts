import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICeb } from 'app/shared/model/ceb.model';
import { CebService } from './ceb.service';

@Component({
    selector: 'jhi-ceb-delete-dialog',
    templateUrl: './ceb-delete-dialog.component.html'
})
export class CebDeleteDialogComponent {
    ceb: ICeb;

    constructor(protected cebService: CebService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cebService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cebListModification',
                content: 'Deleted an ceb'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ceb-delete-popup',
    template: ''
})
export class CebDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ceb }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CebDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ceb = ceb;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ceb', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ceb', { outlets: { popup: null } }]);
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
