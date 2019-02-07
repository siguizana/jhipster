import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeDecision } from 'app/shared/model/type-decision.model';
import { TypeDecisionService } from './type-decision.service';

@Component({
    selector: 'jhi-type-decision-delete-dialog',
    templateUrl: './type-decision-delete-dialog.component.html'
})
export class TypeDecisionDeleteDialogComponent {
    typeDecision: ITypeDecision;

    constructor(
        protected typeDecisionService: TypeDecisionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeDecisionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeDecisionListModification',
                content: 'Deleted an typeDecision'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-decision-delete-popup',
    template: ''
})
export class TypeDecisionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeDecision }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeDecisionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeDecision = typeDecision;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-decision', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-decision', { outlets: { popup: null } }]);
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
