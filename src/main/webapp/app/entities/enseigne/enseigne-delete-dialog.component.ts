import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnseigne } from 'app/shared/model/enseigne.model';
import { EnseigneService } from './enseigne.service';

@Component({
    selector: 'jhi-enseigne-delete-dialog',
    templateUrl: './enseigne-delete-dialog.component.html'
})
export class EnseigneDeleteDialogComponent {
    enseigne: IEnseigne;

    constructor(protected enseigneService: EnseigneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enseigneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enseigneListModification',
                content: 'Deleted an enseigne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enseigne-delete-popup',
    template: ''
})
export class EnseigneDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ enseigne }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnseigneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.enseigne = enseigne;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/enseigne', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/enseigne', { outlets: { popup: null } }]);
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
