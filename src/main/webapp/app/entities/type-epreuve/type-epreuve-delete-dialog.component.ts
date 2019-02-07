import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeEpreuve } from 'app/shared/model/type-epreuve.model';
import { TypeEpreuveService } from './type-epreuve.service';

@Component({
    selector: 'jhi-type-epreuve-delete-dialog',
    templateUrl: './type-epreuve-delete-dialog.component.html'
})
export class TypeEpreuveDeleteDialogComponent {
    typeEpreuve: ITypeEpreuve;

    constructor(
        protected typeEpreuveService: TypeEpreuveService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeEpreuveService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeEpreuveListModification',
                content: 'Deleted an typeEpreuve'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-epreuve-delete-popup',
    template: ''
})
export class TypeEpreuveDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeEpreuve }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeEpreuveDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeEpreuve = typeEpreuve;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-epreuve', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-epreuve', { outlets: { popup: null } }]);
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
