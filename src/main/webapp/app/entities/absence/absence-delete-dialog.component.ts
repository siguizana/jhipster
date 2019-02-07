import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAbsence } from 'app/shared/model/absence.model';
import { AbsenceService } from './absence.service';

@Component({
    selector: 'jhi-absence-delete-dialog',
    templateUrl: './absence-delete-dialog.component.html'
})
export class AbsenceDeleteDialogComponent {
    absence: IAbsence;

    constructor(protected absenceService: AbsenceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.absenceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'absenceListModification',
                content: 'Deleted an absence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-absence-delete-popup',
    template: ''
})
export class AbsenceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ absence }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AbsenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.absence = absence;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/absence', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/absence', { outlets: { popup: null } }]);
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
