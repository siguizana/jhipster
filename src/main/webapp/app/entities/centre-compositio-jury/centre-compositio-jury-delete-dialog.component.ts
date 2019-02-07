import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICentreCompositioJury } from 'app/shared/model/centre-compositio-jury.model';
import { CentreCompositioJuryService } from './centre-compositio-jury.service';

@Component({
    selector: 'jhi-centre-compositio-jury-delete-dialog',
    templateUrl: './centre-compositio-jury-delete-dialog.component.html'
})
export class CentreCompositioJuryDeleteDialogComponent {
    centreCompositioJury: ICentreCompositioJury;

    constructor(
        protected centreCompositioJuryService: CentreCompositioJuryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.centreCompositioJuryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'centreCompositioJuryListModification',
                content: 'Deleted an centreCompositioJury'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-centre-compositio-jury-delete-popup',
    template: ''
})
export class CentreCompositioJuryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ centreCompositioJury }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CentreCompositioJuryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.centreCompositioJury = centreCompositioJury;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/centre-compositio-jury', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/centre-compositio-jury', { outlets: { popup: null } }]);
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
