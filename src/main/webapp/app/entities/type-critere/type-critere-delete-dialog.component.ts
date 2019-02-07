import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCritere } from 'app/shared/model/type-critere.model';
import { TypeCritereService } from './type-critere.service';

@Component({
    selector: 'jhi-type-critere-delete-dialog',
    templateUrl: './type-critere-delete-dialog.component.html'
})
export class TypeCritereDeleteDialogComponent {
    typeCritere: ITypeCritere;

    constructor(
        protected typeCritereService: TypeCritereService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeCritereService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeCritereListModification',
                content: 'Deleted an typeCritere'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-critere-delete-popup',
    template: ''
})
export class TypeCritereDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeCritere }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeCritereDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeCritere = typeCritere;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-critere', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-critere', { outlets: { popup: null } }]);
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
