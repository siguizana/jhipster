import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalleComposition } from 'app/shared/model/salle-composition.model';
import { SalleCompositionService } from './salle-composition.service';

@Component({
    selector: 'jhi-salle-composition-delete-dialog',
    templateUrl: './salle-composition-delete-dialog.component.html'
})
export class SalleCompositionDeleteDialogComponent {
    salleComposition: ISalleComposition;

    constructor(
        protected salleCompositionService: SalleCompositionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salleCompositionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salleCompositionListModification',
                content: 'Deleted an salleComposition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-salle-composition-delete-popup',
    template: ''
})
export class SalleCompositionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salleComposition }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalleCompositionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.salleComposition = salleComposition;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/salle-composition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/salle-composition', { outlets: { popup: null } }]);
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
