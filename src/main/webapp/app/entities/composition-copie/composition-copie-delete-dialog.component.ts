import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompositionCopie } from 'app/shared/model/composition-copie.model';
import { CompositionCopieService } from './composition-copie.service';

@Component({
    selector: 'jhi-composition-copie-delete-dialog',
    templateUrl: './composition-copie-delete-dialog.component.html'
})
export class CompositionCopieDeleteDialogComponent {
    compositionCopie: ICompositionCopie;

    constructor(
        protected compositionCopieService: CompositionCopieService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compositionCopieService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'compositionCopieListModification',
                content: 'Deleted an compositionCopie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-composition-copie-delete-popup',
    template: ''
})
export class CompositionCopieDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compositionCopie }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompositionCopieDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.compositionCopie = compositionCopie;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/composition-copie', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/composition-copie', { outlets: { popup: null } }]);
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
