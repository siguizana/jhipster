import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtapeExamen } from 'app/shared/model/etape-examen.model';
import { EtapeExamenService } from './etape-examen.service';

@Component({
    selector: 'jhi-etape-examen-delete-dialog',
    templateUrl: './etape-examen-delete-dialog.component.html'
})
export class EtapeExamenDeleteDialogComponent {
    etapeExamen: IEtapeExamen;

    constructor(
        protected etapeExamenService: EtapeExamenService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etapeExamenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'etapeExamenListModification',
                content: 'Deleted an etapeExamen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-etape-examen-delete-popup',
    template: ''
})
export class EtapeExamenDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ etapeExamen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EtapeExamenDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.etapeExamen = etapeExamen;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/etape-examen', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/etape-examen', { outlets: { popup: null } }]);
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
