import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDispense } from 'app/shared/model/dispense.model';
import { DispenseService } from './dispense.service';

@Component({
    selector: 'jhi-dispense-delete-dialog',
    templateUrl: './dispense-delete-dialog.component.html'
})
export class DispenseDeleteDialogComponent {
    dispense: IDispense;

    constructor(protected dispenseService: DispenseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dispenseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dispenseListModification',
                content: 'Deleted an dispense'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dispense-delete-popup',
    template: ''
})
export class DispenseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dispense }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DispenseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dispense = dispense;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/dispense', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/dispense', { outlets: { popup: null } }]);
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
