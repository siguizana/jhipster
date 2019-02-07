import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICentreExamen } from 'app/shared/model/centre-examen.model';
import { CentreExamenService } from './centre-examen.service';

@Component({
    selector: 'jhi-centre-examen-delete-dialog',
    templateUrl: './centre-examen-delete-dialog.component.html'
})
export class CentreExamenDeleteDialogComponent {
    centreExamen: ICentreExamen;

    constructor(
        protected centreExamenService: CentreExamenService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.centreExamenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'centreExamenListModification',
                content: 'Deleted an centreExamen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-centre-examen-delete-popup',
    template: ''
})
export class CentreExamenDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ centreExamen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CentreExamenDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.centreExamen = centreExamen;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/centre-examen', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/centre-examen', { outlets: { popup: null } }]);
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
