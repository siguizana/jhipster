import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCentreComposition } from 'app/shared/model/type-centre-composition.model';
import { TypeCentreCompositionService } from './type-centre-composition.service';

@Component({
    selector: 'jhi-type-centre-composition-delete-dialog',
    templateUrl: './type-centre-composition-delete-dialog.component.html'
})
export class TypeCentreCompositionDeleteDialogComponent {
    typeCentreComposition: ITypeCentreComposition;

    constructor(
        protected typeCentreCompositionService: TypeCentreCompositionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeCentreCompositionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeCentreCompositionListModification',
                content: 'Deleted an typeCentreComposition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-centre-composition-delete-popup',
    template: ''
})
export class TypeCentreCompositionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeCentreComposition }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeCentreCompositionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeCentreComposition = typeCentreComposition;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-centre-composition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-centre-composition', { outlets: { popup: null } }]);
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
