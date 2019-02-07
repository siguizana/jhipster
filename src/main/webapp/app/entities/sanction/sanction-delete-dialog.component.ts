import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISanction } from 'app/shared/model/sanction.model';
import { SanctionService } from './sanction.service';

@Component({
    selector: 'jhi-sanction-delete-dialog',
    templateUrl: './sanction-delete-dialog.component.html'
})
export class SanctionDeleteDialogComponent {
    sanction: ISanction;

    constructor(protected sanctionService: SanctionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sanctionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sanctionListModification',
                content: 'Deleted an sanction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sanction-delete-popup',
    template: ''
})
export class SanctionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sanction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SanctionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sanction = sanction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sanction', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sanction', { outlets: { popup: null } }]);
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
