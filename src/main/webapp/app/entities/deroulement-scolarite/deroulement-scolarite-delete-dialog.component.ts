import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeroulementScolarite } from 'app/shared/model/deroulement-scolarite.model';
import { DeroulementScolariteService } from './deroulement-scolarite.service';

@Component({
    selector: 'jhi-deroulement-scolarite-delete-dialog',
    templateUrl: './deroulement-scolarite-delete-dialog.component.html'
})
export class DeroulementScolariteDeleteDialogComponent {
    deroulementScolarite: IDeroulementScolarite;

    constructor(
        protected deroulementScolariteService: DeroulementScolariteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deroulementScolariteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deroulementScolariteListModification',
                content: 'Deleted an deroulementScolarite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deroulement-scolarite-delete-popup',
    template: ''
})
export class DeroulementScolariteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deroulementScolarite }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeroulementScolariteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.deroulementScolarite = deroulementScolarite;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/deroulement-scolarite', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/deroulement-scolarite', { outlets: { popup: null } }]);
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
